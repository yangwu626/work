#!/bin/bash
#Lab10
#Yang Wu
#040920461
#section 320
#myscalc.sh
#2018-11-26
#bash script to maintance users and groups of linux.

#add operation
add () {
	# return $1 + $2 
	val=`expr $1 + $2`
	echo $val
}

#substract operation
substract () {
	# return $1 - $2 
	val=`expr $1 - $2`
	echo $val
}

# helper function to do $1 +/- $2 and output the result
do_calc () {
	if [ $2 == "+" ] ; then
		res=$(add $1 $3)
		echo "The sum of $1 plus $3 equals $res"
	elif [ $2 == "-" ]; then 
		res=$(substract $1 $3)
		echo "The substract of $1 minus $3 equals $res"
	fi
}

# helper function to wait an input from user, 
# it will exit script if user input 'X' or 'x', else it will return the input value.
input_num_or_exit () {
	read num
	if [ $num == "X" ] || [ $num == "x" ]; then
		exit -1
	fi
	echo $num
}

if [ $# == 3 ] ; then
	# if user input three parameters, run it without menu.
	do_calc $1 $2 $3
elif [ $# == 0 ]; then
	# no parameters, enter a loop to show menu and wait for user's input.	
	while true; do 
		# clear screen & show menu 1
		clear
		echo "C) Calculation"
		echo "X) Exit"
		# wait user's input
		read option
		if [ $option == "X" ] || [ $option == "x" ]; then
			# user select 'X' or 'x' to exit
			break;
		elif [ $option == "C" ] || [ $option == "c" ]; then
			# user selected to do calculation, then show menu 2
			echo "Please enter an integer number or X to exit:"
			# call helper function to read a non-'X'/'x' input num, save it to var a.
			a=$(input_num_or_exit)
			# check if a is a number or not
			if ! [ "$a" -eq "$a" ] 2> /dev/null
			then
				echo "Please input integers only"
				continue
			fi	
			# clear screen & show menu 3
			clear
			echo "+) Add"
			echo "-) Subtract"
			echo "X) Exit"
			# wait for user to input operator and save it to op
			read op
			if [ $op == "X" ] || [ $op == "x" ]; then
				# exit script if 'X' or 'x' is selected
				break;
			elif [ $op == "+" ] || [ $op == "-" ]; then
				# if user selected '+' or '-' , show menu 2 to continue
				echo "Please enter an integer number or X to exit:"
				# wait for second num and save it to b.
				b=$(input_num_or_exit)
				# check if b is a number or not
				if ! [ "$b" -eq "$b" ] 2> /dev/null
				then
					# b is not a number, show the wairning and continue, it will abort current calculation and return to the root menu
					echo "Please input integers only"
					continue
				fi	
				# begin to do calculation
				do_calc $a $op $b
			else
				# user input a invalid option, abort current calculation and return to root menu.
				echo "only +/-/X are allowed"
				continue
			fi
		else
			# user input a invalid option, return to root menu
			echo "only C/X are allowed"
			continue
		fi
		# we just did a calculation, wait for 3 seconds and return to root menu.
		sleep 3
	done	# while loop	
fi


