package leros.shared

/*
+--------+----------+
|00000---| nop      |
|000010-0| add      |
|000010-1| addi     |
|000011-0| sub      |
|000011-1| subi     |
|00010---| shr      |
|00011---| -        |
|00100000| load     |
|00100000| load i   |
|00100010| and      |
|00100011| andi     |
|00100100| or       |
|00100101| ori      |
|00100110| xor      |
|00100111| xori     |
|00101001| loadhi   |
|00101010| loadh2i  |
|00101011| loadh3i  |
|00110---| store    |
|001110-?| out      |
|000001-?| in       |
|01000---| jal      |
|01001---| -        |
|01010---| ldaddr   |
|01100--0| loadind  |
|01100--1| loadindbu|
|01110--0| storeind |
|01110--1| storeindb|
|1000nnnn| br       |
|1001nnnn| brz      |
|1010nnnn| brnz     |
|1011nnnn| brp      |
|1100nnnn| brn      |
|11111111| scall    |
+--------+----------+
 */

// Shall we keep this immediate bit in one position and do more complex decoding?
// Or would a simple table just do it?
// If we simple enumerate the 27 instructions, we would just decode 5 bits, freeing another 3 bits
// We could use this to get larger constants in, e.g. up to 15 bits. Useful?
object Constants {
  val NOP = 0x00 // useless
  val ADD = 0x08
  val ADDI = 0x09
  val SUB = 0x0c
  val SUBI = 0x0d // shall we really have a subi?
  val SHR = 0x10
  val LD = 0x20
  val LDI = 0x21
  val AND = 0x22
  val ANDI = 0x23
  val OR = 0x24
  val ORI = 0x25
  val XOR = 0x26
  val XORI = 0x27
  val LDHI = 0x29 // only immediate useful
  val LDH2I = 0x2a
  val LDH3I = 0x2b
  val ST = 0x30
  val OUT = 0x39 // is IN/OUT immediate only?
  val IN = 0x05
  val JAL = 0x40
  val LDADDR = 0x50
  val LDIND = 0x60
  val LDINDBU = 0x61
  val STIND = 0x70
  val STINDB = 0x71
  val BR = 0x80
  val BRZ = 0x90
  val BRNZ = 0xa0
  val BRP = 0xb0
  val BRN = 0xc0
  val SCALL = 0xff // 0 is simulator exit
}
