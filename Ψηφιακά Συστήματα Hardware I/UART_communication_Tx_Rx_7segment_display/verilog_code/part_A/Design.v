`include "Decoder.sv"
`include "Clock_Divider"
`include "BCD_Control"
`timescale 1ns/1ps

//Verilog module.
module TOP(
  input [3:0] digit1,
  input [3:0] digit2,
  input [3:0] digit3,
  input [3:0] digit4,
  input Clk,
  input reset,
  output wire [6:0] Led_Disp,
  output wire [3:0] anode
);
  
  wire [3:0]anode2; 
  wire [3:0] ONE_DIGIT;
  clock_divider C_D(Clk, reset, anode, anode2);
  bcd_control bcdc(digit1, digit2, digit3, digit4, anode2, ONE_DIGIT);
  segment7 decoder(ONE_DIGIT,Led_Disp);
  
  
endmodule