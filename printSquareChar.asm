#title: square character

#Author: Alexander Gavrilov

#filename: squareCharachter.asm

#Description: The program prints square from users input

#input: intInput,charInput - strings displayed for the user,newLine - go to new line string

#output: square out of the character input,the square is of size users input = int from 1 to 25






############################## Data segment ########################################
.data
     intInput:  .asciiz "Please enter number with two digit (decimal) between 1-25\n"
     charInput: .asciiz "please enter the square char to display\n"
     newLine:   .asciiz "\n"

############################## 	Code segment ########################################
.text
           
     main: li $v0,4
           la $a0,intInput #asking from the user for int input
           syscall 
           
           #int input stored in $t0       
           li $v0,5
           syscall
           move $t0,$v0 
           
           #asking from the user for char input
           li $v0,4
           la $a0,charInput
           syscall
           
           #character input stored in $t3
           li $v0,12
           syscall
           move $t3,$v0 
           
           jal printNewLine
           jal printNewLine
           
           #index of outer while loop i=0
           addi $t1,$zero,0 
               
               
               #outer while loop
               while: beq $t1,$t0,exit
                      addi $t2,$zero,0 # index of inner loop is initialized to 0 before we jump to the inner loop
                      j while2
               
               #inner while loop                                                                                    
               while2: beq $t2,$t0,exit2
                   
                       jal printChar
                       addi $t2,$t2,1 
                   
                       j while2
           
           
               exit2:  addi $t2,$zero,0
                       addi $t1,$t1,1   #outer loop index i++
                       jal printNewLine #after finishing printing one line of characters go to new line 
                       j while          #go back to outer loop
            
           #terminate program             
               exit:  li $v0,10
                      syscall
                  
      #procedure that print's the char input            
      printChar: li $v0,11
                 la $a0,0($t3)       
                 syscall
                 jr $ra
      #procedure that prints new line                
   printNewLine: li $v0,4
                 la $a0,newLine
                 syscall 
                 jr $ra         
