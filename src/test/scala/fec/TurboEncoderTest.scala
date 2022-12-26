package fec

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

class InternalInterleaverTest extends AnyFlatSpec with
  ChiselScalatestTester {
  val expectedDataOutIdx = List(0.U(13.W),13.U(13.W),6.U(13.W),19.U(13.W),12.U(13.W),25.U(13.W),18.U(13.W),31.U(13.W),24.U(13.W),37.U(13.W),30.U(13.W),3.U(13.W),36.U(13.W),9.U(13.W),2.U(13.W),15.U(13.W),8.U(13.W),21.U(13.W),14.U(13.W),27.U(13.W),20.U(13.W),33.U(13.W),26.U(13.W),39.U(13.W),32.U(13.W),5.U(13.W),38.U(13.W),11.U(13.W),4.U(13.W),17.U(13.W),10.U(13.W),23.U(13.W),16.U(13.W),29.U(13.W),22.U(13.W),35.U(13.W),28.U(13.W),1.U(13.W),34.U(13.W),7.U(13.W))
  "Internal Interleaver" should "pass" in test(new InternalInterleaver()){ dut =>
    dut.io.dataInLengthK.poke(40.U(13.W))
    for(i <- 0 to 39) {
      dut.io.dataInIdx.poke(i.U(13.W))
      dut.io.dataOutIdx.expect(expectedDataOutIdx(i))
      dut.clock.step()
    }
  }
}