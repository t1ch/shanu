package lte.fec
import chisel3._

class ConstituentEncoder() extends Module {
  val io = IO(new Bundle {
    val dataIn = Input(Bool())
    val valid = Input(Bool())
    val tailingValid = Input(Bool())
    val dataOut = Output(Bool())
  })

  val shiftReg1 = RegInit(false.B)
  val shiftReg2 = RegInit(false.B)
  val shiftReg3 = RegInit(false.B)
  val dataInReg = RegInit(io.dataIn)

  dataInReg     := io.dataIn ^ shiftReg2 ^ shiftReg3
  io.dataOut    :=  dataInReg ^ shiftReg1 ^ shiftReg3

  when(io.valid) {
    shiftReg1 := dataInReg ^ shiftReg1 ^ shiftReg3
    shiftReg2 := shiftReg1
    shiftReg3 := shiftReg2
  }
  when(io.valid & io.tailingValid){
    shiftReg1 := false.B
    shiftReg2 := shiftReg1
    shiftReg3 := shiftReg2
  }
}
