#title: count_abc and count_char 

#Author: Alexander Gavrilov

#filename: ex4.asm

#Description: count the maximal sequence of characters a-z that are in ascending order in a string
#and then count number of times charInput appears

#input: userInput - string with at most 32 spaces,charInput - how many times this char appears in userInput

#output:1.int,number of maximal ascending sequence of a-z chars 2.number of times charInput apperas in UserInput
      

############################## Data segment ########################################
.data
     userInput: .space 32
     stringInput:  .asciiz "Enter string:"
     charInput:  .asciiz "Enter char:"
     stringOutput: .asciiz "Maximal sequence is:"
     stringOutput1:  .asciiz "Number of times the char appears in string is:"
     newLine:   .asciiz "\n"
############################## 	Code segment ########################################

.text
          
     main: #ask for input from user
           li $v0,4
           la $a0,stringInput
           syscall
           
           li $v0,8
           la $a0,userInput #get input from user
           li $a1,32
           move $s0,$a0
           syscall 
           
           li $v0,4
           la $a0,newLine
           syscall
                             
           la   $t0,0($s0)   # adress of first char of user input
           la   $t1,0($s0)   # adress of first char
           la   $t2,1($s0)   #adress of second char
           addi $t3,$zero,0  # maxTemp
           addi $t4,$zero,0  # max
           
           move $a0,$t0      #insert adress of first char into $a0 passed as argument for count_abc
           jal count_abc  
           
    main1:    li $v0,4
              la $a0,newLine
              syscall
    
              #asking from the user for char input
              li $v0,4
              la $a0,charInput
              syscall
           
              #character input stored in $t1
              li $v0,12
              syscall
              move $t1,$v0 
              
              jal printNewLine
           
              move $a0,$t0 #move into a0 adress of string
              move $a1,$t1 #move into a1 char input
              jal count_char
############################## 	count_abc ########################################
          
          count_abc: move $t0,$a0         #t0 points to a[0]
          
          outerLoop: lb   $t5,0($t0)      # t5 = a[i]       
                     move $a0,$t5         # a0 = t5 = a[i]
                     jal  switchEnd
                     jal  range         # 97<=a[i]<=122
                     beq  $v1,1,innerLoop # if ^^^ goto inner while
                    addi  $t0,$t0,1       # else i++, t[i]=t[i+1]
                    la    $t1,0($t0)      # t1 = t0
                    la    $t2,1($t0)      # t2 = t1
                    j     outerLoop
                            
          innerLoop: addi $t3,$t3,1
                     lb   $a0,0($t2)      # load char of A[i+1] into $a0
                     jal  range         # 97<=a[i+1]<=122
                     beq  $v1,1,greater   # if 1 check if A[i]<A[i+1]
                    
                    # end of inner loop,promote pointers to the right place and jump to outerloop
                    add   $t8,$t8,$t3    
                    addi  $t8,$t8,1                                            
                    add   $t0,$t0,$t8        
                    addi  $t8,$zero,0
                    la    $t1,0($t0)
                    la    $t2,1($t0)
                    bgt   $t3,$t4,switch 
                    addi  $t3,$zero,0          
                    j     outerLoop 
            
            #switch between tempMax and max if necessary         
            switch: move $t4,$t3
                    addi $t3,$zero,0
                    j     outerLoop  
           
           # greater checks if a[i]<a[i+1]                    
           greater: lb   $t6,0($t2)         # char A[i+1] in a6,a[i] in a5
                   bgt   $t6,$t5,moveIn     # if A[i+1]>A[i] go to moveIn    
                   add   $t8,$t8,$t3        # else promote all the pointers to the next place in string                                    
                   add   $t0,$t0,$t8        
                   addi  $t8,$zero,0
                    la   $t1,0($t0)
                    la   $t2,1($t0)
                    bgt  $t3,$t4,switch
                    addi $t3,$zero,0              
                    j     outerLoop         # return to outer loop
                    
                                
           #if we got to moveIn we know that a[i]<a[i+1] and they both in range of a-z 
           #so we want to procede and check the next characters
           moveIn:addi $t1,$t1,1
                  lb   $t5,0($t1) 
                  addi $t2,$t2,1
                  j innerLoop
  
  
    # after returning to the outer loop we want to update max and max temp if needed
    switchEnd: bgt $t3,$t4,replace
               jr $ra
    replace:   move $t4,$t3
               addi $t3,$t3,0
               jr $ra
    
    #function range check if the char is in range between 97<=range<=122
    range: bge $a0,97,nextIf # if $a0 >=97
           beq $a0,10,end # if the character equals to 10 terminate program      
          addi $v1,$zero,0 # $a0 < 97 return put in $v1 0 
            jr $ra
   
   nextIf: ble $a0,122,inRange # if $a1 <=122 branch to inRange
          addi $v1,$zero,0  # $a1>122 
            jr $ra   # $v1 is 0, not in range              
          
 inRange: addi $v1,$zero,1
            jr $ra 
    
    end:   bgt  $t4,$t3,finish
           
           li $v0,4
           la $a0,stringOutput
           syscall
           
           move $t4,$t3
           addi $t3,$zero,0
           li   $v0,1
           move $a0,$t4
           syscall
           move $t0,$s0
           jal main1
           
           
  finish:  li $v0,4
           la $a0,stringOutput
           syscall
           
           li $v0,1
           move $a0,$t4
           syscall
           jal printNewLine
           move $t0,$s0
           jal main1
          
        ##############################  end of count_abc ########################################
                   
  count_char: lb $t2,0($t0)# load first char of string into $t2 
              addi $t3,$zero,0 #counter = 0 stored in $t3
            
            
       while: beq $t2,10,exit   #if $t2 = 10 terminate program
              beq $t2,$t1,count #if characters equal counter++
             addi $t0,$t0,1     #move to the next char in string
             lb   $t2,0($t0)    #load the next char into t2
             j    while       
                      
       count:addi $t3,$t3,1     #counter++
             addi $t0,$t0,1     #move to the next char in string
             lb   $t2,0($t0)    #load the next char into t2
             j while       
        
        #print counter and terminate program      
        exit:jal printNewLine
             li $v0,4
             la $a0,stringOutput1
             syscall
             li,$v0,1
             move $a0,$t3
             syscall
             li $v0,10
             syscall
            
            
   #procedure that prints new line                
   printNewLine: li $v0,4
                 la $a0,newLine
                 syscall 
                 jr $ra   
