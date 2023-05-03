// Code your testbench here
// or browse Examples
`timescale 1ns/1ps

module tb_uart_transmitter;
  
  
  reg reset;
  reg Clk; // 50 MHz
  reg Tx_WR; 
  reg Tx_EN = 1;
  reg [7:0] Tx_DATA = 8'b10001010; //8-
  reg [2:0] baud_select = 3'b111;
  
  wire Tx_BUSY;
  wire TxD;
  UART_Transmitter UART_Transmitter_Instance(Clk, reset, Tx_WR, Tx_EN, Tx_DATA, 
                                             baud_select, Tx_BUSY, TxD);
  
  
  initial 
   begin
       $dumpfile("dump.vcd"); $dumpvars;
     Clk = 1'b0;
     Tx_WR = 1'b1;
   reset = 1;
     #10 reset = 0;
     #10 reset = 1;
     #20 Tx_WR <= 0;
   end
	
  always
    begin
      #10 Clk = ~Clk;
    end
  
  
  initial
  #190000 $finish;
  
endmodule