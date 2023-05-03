`timescale 1ns/1ps

module channel(
  input TxD,
  output reg RxD  = 1'bz
);
  
  
  always @(TxD)
    begin
      RxD <= TxD;
      
    end
  
endmodule