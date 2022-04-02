package phy.crc
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class CRCTest extends AnyFlatSpec with
ChiselScalatestTester {
"CRC" should "pass" in {
  test(new CRC(16,16,0x11021,0,false,false,0) ) { dut =>
      dut.reset.poke(true.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.io.data.poke(51966.U)
      dut.clock.step()
      dut.io.valid.poke(true.B)
      dut.clock.step()
      dut.io.crc.expect(63310.U)
    }
  }
}
