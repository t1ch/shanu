package lte.fec
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
class TurboEncoderTest extends AnyFlatSpec with
  ChiselScalatestTester {
  "Constituent Encoder" should "pass" in test(new ConstituentEncoder()).withAnnotations(Seq(WriteVcdAnnotation)){ dut =>
    dut.reset.poke(true.B)
    dut.io.parityEnable.poke(false.B)
    dut.io.tailingEnable.poke(false.B)
    dut.clock.step()
    dut.reset.poke(false.B)
    dut.io.dataIn.poke(true.B)
    dut.io.parityEnable.poke(true.B)
    dut.io.tailingEnable.poke(false.B)
    dut.io.dataOut.expect(true.B)
    dut.clock.step()
    dut.io.dataIn.poke(true.B)
    dut.io.dataOut.expect(false.B)
    dut.clock.step()
    dut.io.dataIn.poke(false.B)
    dut.io.dataOut.expect(false.B)
    dut.clock.step()
    dut.io.dataIn.poke(true.B)
    dut.io.dataOut.expect(true.B)
    dut.clock.step()
    dut.io.dataIn.poke(false.B)
    dut.io.parityEnable.poke(false.B)
    dut.io.tailingEnable.poke(true.B)
    dut.io.dataOut.expect(false.B)
    dut.clock.step()
    dut.io.dataOut.expect(true.B)
    dut.clock.step()
    dut.io.dataOut.expect(true.B)
    dut.clock.step()

  }
}