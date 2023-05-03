`timescale 1ns/1ps

module validation_control(
  input Rx_FERROR,
  input Rx_PERROR,
  input [7:0] Data,
  output reg [7:0] Rx_DATA = 8'bzzzzzzzz,
  output reg Rx_VALID = 0 
);
  
  
  always @(Data)
    begin
      if(Rx_FERROR == 0 && Rx_PERROR == 0 && Data!=0)
        begin
          Rx_DATA = Data;
          Rx_VALID = 1;
          #20 Rx_VALID = 0;
        end
      
      else
        begin
          Rx_VALID = 0;
        end
    end
  
endmodule