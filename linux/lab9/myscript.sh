#!/bin/bash
#Lab9
#Yang Wu
#040920461
#section 320
#myscript.sh
#2018-11-26
#bash script to maintance users and groups of linux.


#loop unless user select "q"/"Q".
while true; do
	# clear screen & `show menu and input user's  selection
	clear
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
        	#select "q",quit loop.
		break
	elif [[ $option == "A" || $option == "a" ]]; then		
		#select a,create a new user account.
		echo "Username: "
		read username
 		echo "User's home directory: "
		read directory
 		echo "Default login shell: "
		read shell
		#add user, create home directory and set default login shell.
		sudo useradd -m -s $shell -d $directory $username
		# check if  useradd success.
		if [ $? -eq 0 ]; then
			echo "Create user account succeeded!"
		else
			echo "Create user account failed!"
		fi
	elif [[ $option == "B" || $option == "b" ]]; then
		#select b,delete an user account.
		echo "Username: "
		read username
		#delete user and user's home directory. 
		sudo userdel -r $username
		#check if user is deleted. 
		if [ $? -eq 0 ]; then
			echo "Delete user succeeded!"
		else
			echo "Delete user failed!"
		fi	
	elif [[ $option == "C" || $option == "c" ]]; then
		#change user's supplemenrary group.
		echo "Username: "
		read username
		echo "Group name: "
		read groupname
		# change user's supplemenrary group.
		sudo adduser $username $groupname
		#check if user's supplemenrary group is changed.
		if [ $? -eq 0 ]; then
			echo " Change supplemenrary group succeeded!"
		else
			echo "Change supplemenrary group operation failed"
		fi	
	elif [[ $option == "D" || $option == "d" ]]; then
		#Change initial group.
		echo "Username: "
		read username
		echo "Group rname: "
		read groupname
		sudo usermod -g $groupname $username
		#check if user's initial group is changed.
		if [ $? -eq 0 ]; then
			echo "Change initial group succeeded!"
		else
			echo "Change initial group failed!"
		fi

	elif [[ $option == "E" || $option == "e" ]]; then
		#Change default login shell.
		echo "Username: "
		read username
		echo "Shell  rname: "
		read shellname
		sudo usermod --shell $shellname $username
		#check if user's default login shell is changed.
		if [ $? -eq 0 ]; then
			echo "Change default login shell succeeded!"
		else
			echo "Change default login shell failed"
		fi

	elif [[ $option == "F" || $option == "f" ]]; then
		#Change account expiration date 
		echo "Username: "
		read username
		echo "Expiry date(YYYY-MM-DD): "
		read expdate
		sudo usermod -e $expdate $username
		##check if expiration date is changed.
		if [ $? -eq 0 ]; then
			echo "Change account expiration date succeeded!"
		else
			echo "Change account expiration date failed"
		fi
		
	else
		echo "Invalid input,please select an option from a - f."
	fi  
	sleep 3
done
