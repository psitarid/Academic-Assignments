// Code your testbench here
// or browse Examples
`timescale 1ns/1ps

module tb_uart_Communication;
  
  reg Clk; // 50 MHz
  reg reset;
  reg [2:0] baud_select = 3'b111;
  reg Tx_WR; 
  reg Tx_EN = 1;
  reg [7:0] Tx_DATA = 8'b10001010; //8-
  //reg [7:0] Tx_DATA = 8'b01010001;
  
  wire Tx_BUSY;
  reg RxD = 1'bz;
  
  integer Tx_BUSY_counter = 0;
   reg Rx_EN = 1;
   wire Rx_PERROR;
   wire Rx_FERROR;
   wire Rx_VALID;
   wire [7:0] Rx_DATA;
  
  
  UART UART_instance(Clk, reset, baud_select, Tx_WR, Tx_EN, Tx_DATA, Rx_EN, Tx_BUSY, Rx_PERROR, Rx_FERROR, 	 Rx_VALID, Rx_DATA);
  
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
  
  always@(Tx_BUSY)
    begin      
      if (Tx_BUSY_counter ==1)
        begin
          Tx_DATA <= 8'b11111111; 
        end
      
      if(Tx_BUSY_counter == 2)
        begin
          Tx_WR = 1'b1;
          #20 Tx_WR <= 0;
        end
        Tx_BUSY_counter  = Tx_BUSY_counter +1; 
    end
  
  
  initial
  #190000 $finish;
  
endmodule