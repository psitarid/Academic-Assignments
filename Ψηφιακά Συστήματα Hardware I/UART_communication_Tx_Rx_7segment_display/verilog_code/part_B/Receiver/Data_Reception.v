`timescale 1ns/1ps

module data_reception(
  input RxD,
  input Rx_EN,
  input RX_sample_ENABLE,
  output reg [10:0] packet = 11'bzzzzzzzzzzz,
  output reg start_bit_sync = 0,
  
  output reg packet_completion = 0 //Updates to 1 when packet is ready.
);
  integer duration_counter = 0; // Duration of the packet transfer.
  
  integer iteration = 0; //Runs through the packet Array.
  integer sync_counter = 1; //Used to check sampling synchronization
  
  
  integer ones = 0;
  integer zeroes = 0;

      always @(posedge RX_sample_ENABLE)
        begin    
          if(RxD != 1 || duration_counter != 0) // Sampling on the arrival of packet
            begin        
          
          if(sync_counter == 1)
            begin
              zeroes = 0;
              ones = 0;
            end
          
          if(RxD == 1) // Responsible for noise tracking.
           begin 
           	ones = ones + 1;
           end
          else
            begin
              zeroes = zeroes + 1;
            end
          
          
          
          
          if (sync_counter == 16) //8
           begin
             if(zeroes > ones) // Checking the real value of RxD, despite noise.
               begin
                 packet[iteration] <= 0;
               end
             else
               begin
                 packet[iteration] <= 1;
               end
          	  iteration = iteration + 1;
              sync_counter = 0; // Sampling will continue after 16 cycles.
           end
  
        
      
         if (iteration == 11)
          begin
            packet_completion = 1; //Packet has been formed!
            iteration = 0;
            packet_completion = 0;
          end
          
          
          if(duration_counter == 16 && RxD ==0) 
            // Ensures that start_bit has arrived!
            begin
              if (zeroes + ones == duration_counter)
                
                // No framing error.
                begin
          	start_bit_sync <= 1;
                  
                  // At the 8th Rx_sample_Enable beat, the start bit and then 					  the whole packet gets sampled.
                end
            end
          
          if(duration_counter == 176) 
            // Duration counter will count until the 16th Rx_sample_ENABLE beat 		    of the 11th bit, which indicates the end of the packet. 
            begin
              duration_counter = -1;
              
              // Resets, in order to start counting for the next packet.
              
              start_bit_sync = 0 ;
              // Resets to zero until the next packet comes. 
            end
          
          
          
          
  		sync_counter = sync_counter +1;
        duration_counter = duration_counter + 1;
              
            end
        end
   
    
endmodule