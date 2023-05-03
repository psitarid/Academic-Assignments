`timescale 1ns/1ps

module digit_producer(
  input [7:0] Rx_DATA,
  input Rx_FERROR,
  input Rx_PERROR,
  output reg [3:0] digit1,
  output reg [3:0] digit2,
  output reg [3:0] digit3,
  output reg [3:0] digit4
);
  
  integer rx_data_counter = -1; // Rx_DATA arrival counter
  integer error_counter = 0;   // Error counter
  integer i = 0;
  integer completion = 0; // All is set.
  
  reg [3:0] sub1 = 4'bzzzz; //Assistance variables
  reg [3:0] sub2 = 4'bzzzz;
  reg [3:0] sub3 = 4'bzzzz;
  reg [3:0] sub4 = 4'bzzzz;
  
  always@(Rx_DATA)
  	begin
      
      if(rx_data_counter == 0) // Arrival of first packet
        begin
          if(Rx_PERROR == 0 && Rx_FERROR == 0) // No errors.
            begin
              for(i=0;i<4;i=i+1)
                begin
                  sub1[i]<=Rx_DATA[i];
                  sub2[i]<=Rx_DATA[i+4];
                end
            end
        end
      
      else if(rx_data_counter == 1) //Arrival of second packet.
        begin
          if(Rx_PERROR == 0 && Rx_FERROR == 0) // no errors.
            begin
              for(i=0;i<4;i=i+1)
                begin
                  sub3[i]=Rx_DATA[i];
                  sub4[i]=Rx_DATA[i+4];
                end
              rx_data_counter = -1;
              completion = 1;
            end
        end
      
      if(Rx_PERROR == 1 || Rx_FERROR == 1) // Checks if errors occured!
        begin
          error_counter = 1;
        end
      
      if(error_counter ==1 && rx_data_counter == 1) //Display F
        begin
                  digit1<=1011; //F
                  digit2<=1011; //F
                  digit3<=1011; //F
                  digit4<=1011; //F
                  
                  error_counter=0;
                  rx_data_counter= -1;
                  completion =0;
            end
      
      if(completion ==1) //Display the full information
        begin
          //First data packet
          digit2<= sub1;
          digit1<= sub2;
          
          //Second data packet
          digit3<= sub3;
          digit4<= sub4;
          
          completion = 0;
        end
      rx_data_counter = rx_data_counter + 1;
    end
  
  
endmodule