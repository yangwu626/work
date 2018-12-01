#!/usr/bin/bash
#Lab9
#Yang Wu
#040920461
#section 320
#myscript
#bash script to maintance users and groups of linux.


option="a"
while true; do
	# show menu and input user's  selection
	echo "A) Create a user account"  
	echo "B) Delete a user account"  
	echo "C) Change supplemenrary group for a user account"  
	echo "D) Change innitial group for a user account"  
	echo "E) Change default login shell for a user account"  
	echo "F) Change account expiration date for  a user account"  
	echo "Q) Quit"  
	echo "Please select a option: "
	read option

 	if [ $option == "Q" ] || [ $option == "q" ]; then
		break
	elif [[ $option == "A" || $option == "a" ]]; then		
		#select a,create a new user account.
		echo "Username: "
		read username
 		echo "Use's home directory: "
		read directory
 		echo "Default login shell: "
		read shell
		sudo useradd -m -s $shell -d $directory $username

		if [ $? -eq 0 ]; then
			echo "operation succeeded!"
		else
			echo "operation failed"
		fi
	elif [[ $option == "B" || $option == "b" ]]; then
		echo "Username: "
		read username
		sudo userdel -r $username
		if [ $? -eq 0 ]; then
			echo "operation succeeded!"
		else
			echo "operation failed"
		fi	
	elif [[ $option == "C" || $option == "c" ]]; then
		echo "Username: "
		read username
		echo "Group rname: "
		read groupname
		sudo adduser $username $groupname
		if [ $? -eq 0 ]; then
			echo "operation succeeded!"
		else
			echo "operation failed"
		fi	
	elif [[ $option == "D" || $option == "d" ]]; then
		echo "Username: "
		read username
		echo "Group rname: "
		read groupname
		sudo usermod -g $groupname $username
		if [ $? -eq 0 ]; then
			echo "operation succeeded!"
		else
			echo "operation failed"
		fi

	elif [[ $option == "E" || $option == "e" ]]; then
		echo "Username: "
		read username
		echo "Shell  rname: "
		read shellname
		sudo usermod --shell $shellname $username
		if [ $? -eq 0 ]; then
			echo "operation succeeded!"
		else
			echo "operation failed"
		fi

	elif [[ $option == "F" || $option == "f" ]]; then
		echo "Username: "
		read username
		echo "Expiry date(YYY-MM-DD): "
		read expdate
		sudo usermod -e $expdate $username
		if [ $? -eq 0 ]; then
			echo "operation succeeded!"
		else
			echo "operation failed"
		fi
		
	else
		echo "Invalid input,please select an option from a - f."
	fi  
	sleep 3
done
