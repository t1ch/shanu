module CRC(
  input         clock,
  input         reset,
  input  [15:0] io_data,
  input         io_valid,
  output [15:0] io_crc,
  output        io_matches
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
`endif // RANDOMIZE_REG_INIT
  reg [15:0] crcReg; // @[CRC.scala 228:23]
  reg [15:0] dataIn; // @[CRC.scala 229:19]
  wire  _T_14 = crcReg[0] ^ crcReg[4] ^ crcReg[8] ^ crcReg[11] ^ crcReg[12]; // @[CRC.scala 259:15]
  wire  crcRegNext_0 = _T_14 ^ dataIn[0] ^ dataIn[4] ^ dataIn[8] ^ dataIn[11] ^ dataIn[12]; // @[CRC.scala 264:15]
  wire  _T_44 = crcReg[1] ^ crcReg[5] ^ crcReg[9] ^ crcReg[12] ^ crcReg[13]; // @[CRC.scala 259:15]
  wire  crcRegNext_1 = _T_44 ^ dataIn[1] ^ dataIn[5] ^ dataIn[9] ^ dataIn[12] ^ dataIn[13]; // @[CRC.scala 264:15]
  wire  _T_74 = crcReg[2] ^ crcReg[6] ^ crcReg[10] ^ crcReg[13] ^ crcReg[14]; // @[CRC.scala 259:15]
  wire  crcRegNext_2 = _T_74 ^ dataIn[2] ^ dataIn[6] ^ dataIn[10] ^ dataIn[13] ^ dataIn[14]; // @[CRC.scala 264:15]
  wire  _T_104 = crcReg[3] ^ crcReg[7] ^ crcReg[11] ^ crcReg[14] ^ crcReg[15]; // @[CRC.scala 259:15]
  wire  crcRegNext_3 = _T_104 ^ dataIn[3] ^ dataIn[7] ^ dataIn[11] ^ dataIn[14] ^ dataIn[15]; // @[CRC.scala 264:15]
  wire  _T_131 = crcReg[4] ^ crcReg[8] ^ crcReg[12] ^ crcReg[15]; // @[CRC.scala 259:15]
  wire  crcRegNext_4 = _T_131 ^ dataIn[4] ^ dataIn[8] ^ dataIn[12] ^ dataIn[15]; // @[CRC.scala 264:15]
  wire  _T_167 = crcReg[0] ^ crcReg[4] ^ crcReg[5] ^ crcReg[8] ^ crcReg[9] ^ crcReg[11] ^ crcReg[12] ^ crcReg[13]; // @[CRC.scala 259:15]
  wire  crcRegNext_5 = _T_167 ^ dataIn[0] ^ dataIn[4] ^ dataIn[5] ^ dataIn[8] ^ dataIn[9] ^ dataIn[11] ^ dataIn[12] ^
    dataIn[13]; // @[CRC.scala 264:15]
  wire  _T_215 = crcReg[1] ^ crcReg[5] ^ crcReg[6] ^ crcReg[9] ^ crcReg[10] ^ crcReg[12] ^ crcReg[13] ^ crcReg[14]; // @[CRC.scala 259:15]
  wire  crcRegNext_6 = _T_215 ^ dataIn[1] ^ dataIn[5] ^ dataIn[6] ^ dataIn[9] ^ dataIn[10] ^ dataIn[12] ^ dataIn[13] ^
    dataIn[14]; // @[CRC.scala 264:15]
  wire  _T_263 = crcReg[2] ^ crcReg[6] ^ crcReg[7] ^ crcReg[10] ^ crcReg[11] ^ crcReg[13] ^ crcReg[14] ^ crcReg[15]; // @[CRC.scala 259:15]
  wire  crcRegNext_7 = _T_263 ^ dataIn[2] ^ dataIn[6] ^ dataIn[7] ^ dataIn[10] ^ dataIn[11] ^ dataIn[13] ^ dataIn[14] ^
    dataIn[15]; // @[CRC.scala 264:15]
  wire  _T_308 = crcReg[3] ^ crcReg[7] ^ crcReg[8] ^ crcReg[11] ^ crcReg[12] ^ crcReg[14] ^ crcReg[15]; // @[CRC.scala 259:15]
  wire  crcRegNext_8 = _T_308 ^ dataIn[3] ^ dataIn[7] ^ dataIn[8] ^ dataIn[11] ^ dataIn[12] ^ dataIn[14] ^ dataIn[15]; // @[CRC.scala 264:15]
  wire  _T_347 = crcReg[4] ^ crcReg[8] ^ crcReg[9] ^ crcReg[12] ^ crcReg[13] ^ crcReg[15]; // @[CRC.scala 259:15]
  wire  crcRegNext_9 = _T_347 ^ dataIn[4] ^ dataIn[8] ^ dataIn[9] ^ dataIn[12] ^ dataIn[13] ^ dataIn[15]; // @[CRC.scala 264:15]
  wire  _T_380 = crcReg[5] ^ crcReg[9] ^ crcReg[10] ^ crcReg[13] ^ crcReg[14]; // @[CRC.scala 259:15]
  wire  crcRegNext_10 = _T_380 ^ dataIn[5] ^ dataIn[9] ^ dataIn[10] ^ dataIn[13] ^ dataIn[14]; // @[CRC.scala 264:15]
  wire  _T_410 = crcReg[6] ^ crcReg[10] ^ crcReg[11] ^ crcReg[14] ^ crcReg[15]; // @[CRC.scala 259:15]
  wire  crcRegNext_11 = _T_410 ^ dataIn[6] ^ dataIn[10] ^ dataIn[11] ^ dataIn[14] ^ dataIn[15]; // @[CRC.scala 264:15]
  wire  _T_440 = crcReg[0] ^ crcReg[4] ^ crcReg[7] ^ crcReg[8] ^ crcReg[15]; // @[CRC.scala 259:15]
  wire  crcRegNext_12 = _T_440 ^ dataIn[0] ^ dataIn[4] ^ dataIn[7] ^ dataIn[8] ^ dataIn[15]; // @[CRC.scala 264:15]
  wire  _T_467 = crcReg[1] ^ crcReg[5] ^ crcReg[8] ^ crcReg[9]; // @[CRC.scala 259:15]
  wire  crcRegNext_13 = _T_467 ^ dataIn[1] ^ dataIn[5] ^ dataIn[8] ^ dataIn[9]; // @[CRC.scala 264:15]
  wire  _T_491 = crcReg[2] ^ crcReg[6] ^ crcReg[9] ^ crcReg[10]; // @[CRC.scala 259:15]
  wire  crcRegNext_14 = _T_491 ^ dataIn[2] ^ dataIn[6] ^ dataIn[9] ^ dataIn[10]; // @[CRC.scala 264:15]
  wire  _T_515 = crcReg[3] ^ crcReg[7] ^ crcReg[10] ^ crcReg[11]; // @[CRC.scala 259:15]
  wire  crcRegNext_15 = _T_515 ^ dataIn[3] ^ dataIn[7] ^ dataIn[10] ^ dataIn[11]; // @[CRC.scala 264:15]
  wire [7:0] crcReg_lo = {crcRegNext_7,crcRegNext_6,crcRegNext_5,crcRegNext_4,crcRegNext_3,crcRegNext_2,crcRegNext_1,
    crcRegNext_0}; // @[CRC.scala 269:26]
  wire [15:0] _crcReg_T = {crcRegNext_15,crcRegNext_14,crcRegNext_13,crcRegNext_12,crcRegNext_11,crcRegNext_10,
    crcRegNext_9,crcRegNext_8,crcReg_lo}; // @[CRC.scala 269:26]
  assign io_crc = crcReg; // @[CRC.scala 243:22]
  assign io_matches = crcReg == 16'h0; // @[CRC.scala 276:27]
  always @(posedge clock) begin
    if (reset) begin // @[CRC.scala 228:23]
      crcReg <= 16'h0; // @[CRC.scala 228:23]
    end else if (io_valid) begin // @[CRC.scala 254:18]
      crcReg <= _crcReg_T; // @[CRC.scala 269:12]
    end
    dataIn <= io_data; // @[CRC.scala 236:12]
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  crcReg = _RAND_0[15:0];
  _RAND_1 = {1{`RANDOM}};
  dataIn = _RAND_1[15:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
