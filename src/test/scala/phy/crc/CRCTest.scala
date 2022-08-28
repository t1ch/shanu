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
      dut.io.valid.poke(true.B)
      dut.clock.step(2)
      dut.io.crc.expect(63310.U)
    }
  }

  "CRC Check" should "pass" in {
  test(new CRC(16,32,0x11021,0,false,false,0) ) { dut =>
      dut.clock.step()
      dut.reset.poke(true.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.io.data.poke(3405707086L.U)
      dut.io.valid.poke(true.B)
      dut.clock.step(2)
      dut.io.matches.expect(true.B)
    }
  }

  "CRC Check for a message of length that is a multiple of polynomial" should "pass" in {
    test(new CRC(8,8,0xD5,0,false,false,0)){dut =>
      dut.clock.step()
      dut.reset.poke(true.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.clock.step()
      dut.io.data.poke("hca".U)
      dut.io.valid.poke(true.B)
      dut.clock.step()
      dut.io.data.poke("hfe".U)
      dut.io.valid.poke(true.B)
      dut.clock.step()
      dut.io.data.poke("hba".U)
      dut.io.valid.poke(true.B)
      dut.clock.step()
      dut.io.data.poke("hbe".U)
      dut.io.valid.poke(true.B)
      dut.clock.step(2)
      dut.io.crc.expect(209.U)
    }
  }
}

class CRC16Test extends AnyFlatSpec with
  ChiselScalatestTester {
  "CRC16Test" should "pass" in {
    test(new CRC(16, 16, 0x11021, 0, false, false, 0)).withAnnotations(Seq( WriteVcdAnnotation ))
    { dut =>
      dut.reset.poke(true.B)
      dut.clock.step()
      dut.reset.poke(false.B)
      dut.io.data.poke(51966.U)
      dut.io.valid.poke(true.B)
      dut.clock.step(2)
      dut.io.crc.expect(63310.U)
    }
  }
}