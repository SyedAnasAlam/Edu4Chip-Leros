package leros

import chisel3._
import chisel3.util._
import wrmem._
import leros.LerosConfig._

class LerosTop(prog : String) extends Module with RequireAsyncReset {
  val io = IO(new Bundle {
    val led = Output(UInt(16.W)) 
    val rx = Input(UInt(1.W))
  })

  val invertReset = ~reset.asBool

  val wrInstrMem = withReset(invertReset.asAsyncReset) {
    Module(new WrInstrMem(MEM_ADDR_WIDTH, CLOCK_FREQ_HZ, UART_BAUDRATE))
  }
  val lerosCore = withReset(wrInstrMem.io.coreReset.asAsyncReset) { 
    Module(new Leros(prog, DATA_WIDTH, MEM_ADDR_WIDTH))
  }

  wrInstrMem.io.pc := lerosCore.io.pc
  lerosCore.io.instr := wrInstrMem.io.instr
  wrInstrMem.io.uartRX := io.rx

  io.led := lerosCore.io.led
}

object LerosTop extends App {
  emitVerilog(new LerosTop(" "), Array("--target-dir", "generated"))
}