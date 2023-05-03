// Code your testbench here
// or browse Examples
`timescale 1ns/1ps

module tb_uart_receiver;
  integer nothing =0 ;

  integer i;
  reg reset;
  reg Clk; // 50 MHz 
  reg Rx_EN = 1;
  //reg [10:0] word = 11'b00101000111;    //8'b10001010;
  reg [10:0] word = 11'b01101010001;
  reg [2:0] baud_select = 3'b111;
  reg RxD = 1; 
  
  wire Rx_PERROR;
  wire Rx_FERROR;
  wire [7:0] Rx_DATA;
  wire Rx_VALID;

  UART_Receiver tb_uart_receiver_instance(RxD, Rx_EN, baud_select, Clk, reset,
                                         Rx_PERROR, Rx_FERROR,Rx_VALID,Rx_DATA);

  
  
  initial
   begin
       $dumpfile("dump.vcd"); $dumpvars;
     Clk = 1'b0;
   reset = 1;
     #10 reset = 0;
     #10 reset = 1;
     for(i = 10; i>-1; i = i-1)
       begin
         RxD <= word[i]; 
         #8680 nothing = nothing; // 1/115200 * 10^9
         //#8700 nothing = nothing; //8680 + 20(Clock)     	 
       end
   end
  
  
  always
    begin
      #10 Clk = ~Clk;
    end
  
  
  
  initial
  #190000 $finish;
  
  
endmodule