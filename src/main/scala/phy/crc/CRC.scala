/* This module is a translation of Adam Greig's (agg) https://github.com/adamgreig Amaranth implementation of the CRC
 */
package phy.crc
import Chisel.Reverse
import chisel3._
import scala.collection.mutable.ArrayBuffer

class SoftwareCRC(
                    n: Int,
                    m: Int,
                    polynomial: Int,
                    init: Int,
                    reflectInput: Boolean,
                    reflectOutput: Boolean,
                    xorOut: Int
                  ) {
  /*
    Compute CRCs in software, used to create constants required by the ``CRC``
    class. Refer to its documentation for the meaning of each parameter.
   */

  def update(crc: Int, word: Int): Int = {
    /*
        Run one round of CRC processing in software, given the current CRC
        value ``crc`` and the new data word ``word``.

        This method is not affected by ``init``, ``ref_in``, ``ref_out``,
        or ``xor_out`; those parameters are applied elsewhere.

        Returns the new value for ``crc``.


       Implementation notes:
        # Implementation notes:
        # We always compute most-significant bit first, which means the
        # polynomial and initial value may be used as-is, and the ref_in
        # and ref_out values have their usual sense.
        # However, when computing word-at-a-time and MSbit-first, we must
        # align the input word so its MSbit lines up with the MSbit of the
        # previous CRC value. When the CRC length n is smaller than the word
        # length m, this would normally truncate data bits.
        # Instead, we shift the previous CRC left by m and the word left by
        # n, lining up their MSbits no matter the relation between n and m.
        # The new CRC is then shifted right by m before output.
     */
    var crcTemp = (crc << m) ^ (word << n)
    for (_ <- 1 to m) {
      if (((crcTemp >> m) & (1 << (n - 1))) > 0) {
        crcTemp = (crcTemp << 1) ^ (polynomial << m)
      } else {
        crcTemp <<= 1
      }
    }
    (crcTemp >> m) & (math.pow(2, n).toInt - 1)
  }

  def reflect(word: Int): Int = {
    java.lang.Integer.reverse(word)
  }

  def compute(data: Array[Int]): Int = {
    /*
        Compute in software the CRC of the input data words in ``data``.

        Returns the final CRC value.
     */

    var crc = init
    for (word <- data) {
      crc = update(crc, if (reflectInput) reflect(word) else word)
    }
    if (reflectOutput)
      crc = reflect(crc)
    crc ^= xorOut
    crc
  }

  def residue(): Int = {
    /*
        Compute in software the residue for the configured CRC, which is the
        value left in the CRC register after processing a valid codeword.

         Residue is computed by initialising to (reflected) xor_out, feeding
         n 0 bits, then taking the (reflected) output, without any XOR.
    */
    val init = if (reflectOutput) reflect(xorOut) else xorOut
    val crc = new SoftwareCRC(n, n, polynomial, init, false, reflectOutput, 0)
    crc.compute(Array(0))
  }

  def generateMatrices(): (ArrayBuffer[Array[Boolean]], ArrayBuffer[Array[Boolean]]) = {
    /*
        Computes the F and G matrices for parallel CRC computation, treating
        the CRC as a linear time-invariant system described by the state
        relation x(t+1) = F.x(i) + G.u(i), where x(i) and u(i) are column
        vectors of the bits of the CRC register and input word, F is the n-by-n
        matrix relating the old state to the new state, and G is the n-by-m
        matrix relating the new data to the new state.

        The matrices are ordered least-significant-bit first; in other words
        the first entry, with index (0, 0), corresponds to the effect of the
        least-significant bit of the input on the least-significant bit of the
        output.

        For convenience of implementation, both matrices are returned
        transposed: the first index is the input bit, and the second index is
        the corresponding output bit.

        The matrices are used to select which bits are XORd together to compute
        each bit i of the new state: if F[j][i] is set then bit j of the old
        state is included in the XOR, and if G[j][i] is set then bit j of the
        new data is included.

        These matrices are not affected by ``init``, ``ref_in``, ``ref_out``,
        or ``xor_out``.
  */
    val f, g = new ArrayBuffer[Array[Boolean]]()
    for (i <- 0 until n) {
      val w = update(math.pow(2, i).toInt, 0)
      f += String.format("%" + n + "s", w.toBinaryString).replace(' ', '0').reverse.map(x => if (x == '1') true else false).toArray
    }
    for (i <- 0 until m) {
      val w = update(0, math.pow(2, i).toInt)
      g += String.format("%" + n + "s", w.toBinaryString).replace(' ', '0').reverse.map(x => if (x == '1') true else false).toArray
    }
    (f, g)
  }
}
class CRC(
           n:Int,
           m:Int,
           polynomial:Int,
           init:Int,
           reflectInput:Boolean,
           reflectOutput:Boolean,
           xorOut:Int) extends Module {
  /*Cyclic redundancy check (CRC) generator.

    This module generates CRCs from an input data stream, which can be used
    to validate an existing CRC or generate a new CRC. It can handle most
    forms of CRCs, with selectable polynomial, input data width, initial
    value, output XOR, and bit-reflection on input and output.

    It is parameterised using the well-known Williams model from
    "A Painless Guide to CRC Error Detection Algorithms":
    http://www.ross.net/crc/download/crc_v3.txt

    For the parameters to use for standard CRCs, refer to:

    * `reveng`_'s catalogue, which uses an identical parameterisation,
    * `crcmod`_'s list of predefined functions, but remove the leading '1'
      from the polynominal and where "Reversed" is True, set both ``ref_in``
      and ``ref_out`` to True,
    * `CRC Zoo`_, which contains only polynomials; use the "explicit +1"
      form of polynomial but remove the leading '1'.

    .. _reveng: https://reveng.sourceforge.io/crc-catalogue/all.htm
    .. _crcmod: http://crcmod.sourceforge.net/crcmod.predefined.html
    .. _CRC Zoo: https://users.ece.cmu.edu/~koopman/crc/

    The CRC value is updated on any clock cycle where ``valid`` is asserted,
    with the updated value available on the ``crc`` output on the subsequent
    clock cycle. The latency is therefore one cycle, and the throughput is
    one data word per clock cycle.

    With ``m=1``, a classic bit-serial CRC is implemented for the given
    polynomial in a Galois-type shift register. For larger values of m,
    a similar architecture computes every new bit of the CRC in parallel.

    The ``match`` output may be used to validate data with a trailing CRC
    (also known as a codeword). If the most recently processed word(s) form
    the valid CRC of all the previous data since reset, the CRC register
    will always take on a fixed value known as the residue. The ``match``
    output indicates whether the CRC register currently contains this residue.

    Parameters
    ----------
    n : Int
        Bit width of CRC word. Also known as "width" in the Williams model.
    m : Int
        Bit width of data words.
    polynomial : Int
        CRC polynomial to use, n bits long, without the implicit x**n term.
        Polynomial is always specified with the highest order terms in the
        most significant bit positions; use ``reflectInput`` and ``reflectOutput`` to
        perform a least significant bit first computation.
    init : Int
        Initial value of CRC register at reset. Most significant bit always
        corresponds to the highest order term in the CRC register.
    reflectInput : Boolean
        If True, the input data words are bit-reflected, so that they are
        processed least significant bit first.
    reflectOutput : Boolean
        If True, the output CRC is bit-reflected, so the least-significant bit
        of the output is the highest-order bit of the CRC register.
        Note that this reflection is performed over the entire CRC register;
        for transmission you may want to treat the output as a little-endian
        multi-word value, so for example the reflected 16-bit output 0x4E4C
        would be transmitted as the two octets 0x4C 0x4E, each transmitted
        least significant bit first.
    xorOut : Int
        The output CRC will be the CRC register XOR'd with this value, applied
        after any output bit-reflection.

    Attributes
    ----------
    data :
        Data word to add to CRC when ``valid`` is asserted.
    valid :
        Assert when ``data`` is valid to add the data word to the CRC.
    crc :
        Registered CRC output value, updated one clock cycle after ``valid``
        becomes asserted.
    matches :
        Asserted if the current CRC value indicates a valid codeword has been
        received.
    */
  val io = IO(new Bundle {
    val data = Input(UInt(m.W))
    val valid = Input(Bool())
    val crc = Output(UInt(n.W))
    val matches = Output(Bool())
  })

  val crcReg = RegInit(init.U(n.W))
  val dataIn = Reg(UInt(m.W))
  val softwareCRC = new SoftwareCRC(n, m, polynomial, init, reflectInput, reflectOutput, xorOut)

  //Optionally bit -reflect input words.
  if (reflectInput) {
    dataIn := Reverse(io.data)
  } else {
    dataIn := io.data
  }

  //Optionally bit -reflect and then XOR the output from the state.
  if (reflectOutput) {
    io.crc := Reverse(crcReg) ^ xorOut.U
  } else {
    io.crc := crcReg ^ xorOut.U
  }
  //Compute CRC matrices and expected residue using the software model
  val (f, g) = softwareCRC.generateMatrices()
  val residue = softwareCRC.residue()

  //Compute next CRC state

  val crcRegNext = Wire(Vec(n, Bool()))
  crcRegNext := DontCare

  when(io.valid) {
    for (i <- 0 until n) {
      var bit = false.B
      for (j <- 0 until n) {
        if (f(j)(i)) {
          bit ^= Mux(crcReg(j), true.B, false.B)
        }
      }
      for (j <- 0 until m) {
        if (g(j)(i)) {
          bit ^= Mux(dataIn(j), true.B, false.B)
        }
      }
      crcRegNext(i) := bit
    }
    crcReg := crcRegNext.asUInt
  }

  //Check for residue match indicating a valid codeword
  if (reflectOutput) {
    io.matches := (Reverse(crcReg) === residue.U)
  } else {
    io.matches := (crcReg === residue.U)
  }
}

  object Generate extends App{
  emitVerilog(new CRC(16,16,0x1021,0,false,false,0),Array("--target-dir","generated"))
}
