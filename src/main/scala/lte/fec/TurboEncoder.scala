package lte.fec
import chisel3._

class ConstituentEncoder() extends Module {
  val io = IO(new Bundle {
    val dataIn = Input(Bool())
    val parityEnable = Input(Bool())
    val tailingEnable = Input(Bool())
    val dataOut = Output(Bool())
  })

  val shiftReg1  = RegInit(false.B)
  val shiftReg2  = RegInit(false.B)
  val shiftReg3  = RegInit(false.B)

  io.dataOut    :=  io.dataIn ^ shiftReg2 ^ shiftReg3 ^ shiftReg1 ^ shiftReg3
  shiftReg2 := shiftReg1
  shiftReg3 := shiftReg2
  when(io.parityEnable) {
    shiftReg1     := io.dataIn ^ shiftReg2 ^ shiftReg3
  }
  when(io.tailingEnable){
    io.dataOut    :=  io.dataIn ^ shiftReg1 ^ shiftReg3
    shiftReg1     := false.B
  }
}