`timescale 1ns/1ps
module tb_segment7;

  reg reset;
  reg Clk; // 50 MHz
  reg [3:0] bcd1 = 4'b1010; //-
  reg [3:0] bcd2 = 4'b0001; //1
  reg [3:0] bcd3 = 4'b1001; //9
  reg [3:0] bcd4 = 4'b0100; //4
  
  wire [6:0] Led_Disp;
  wire [3:0] anode;

  TOP uut(bcd1 ,bcd2 ,bcd3, bcd4, Clk, reset, Led_Disp, anode);
  

  
 initial 
   begin
       $dumpfile("dump.vcd"); $dumpvars;
     Clk = 1'b0;
   
   reset = 1;
     #10 reset = 0;
     #10 reset = 1;   
     
   end
	
  always
    begin
      #10 Clk = ~Clk;
    end
  
  
  initial
  #10880 $finish;    
  //10880
endmodule