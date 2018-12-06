<?php include 'header.php' ?>
<?php require_once('./dao/customerDAO.php'); ?>
<div id="content" class="clearfix">
    <aside>
    <h2>Mailing Address</h2>
    <h3>1385 Woodroffe Ave<br>
    Ottawa, ON K4C1A4</h3>
    <h2>Phone Number</h2>
    <h3>(613)727-4723</h3>
    <h2>Fax Number</h2>
    <h3>(613)555-1212</h3>
    <h2>Email Address</h2>
    <h3>info@wpeatery.com</h3>
    </aside>
    <div class="main">
    <h1>Sign up for our newsletter</h1>
    <p>Please fill out the following form to be kept up to date with news, specials, and promotions from the WP eatery!</p>
	<?php
		// regular expressions to ensure that the email address format
		function valid_email($str) {
			//return filter_var($_POST['emailAddress'], FILTER_VALIDATE_EMAIL)
			return (!preg_match("/^([a-z0-9\+_\-]+)(\.[a-z0-9\+_\-]+)*@([a-z0-9\-]+\.)+[a-z]{2,6}$/ix", $str)) ? FALSE : TRUE;
		}
		//regular expressions to ensure that the phone number format(123945678 or 123-456-7890)
		function valid_phone_number($phone) {
			return preg_match("/^[0-9]{3}-?[0-9]{3}-?[0-9]{4}$/", $phone) ? TRUE : FALSE;
		}

		//sumit acction
		try{
			//connect database.
			$customerDAO = new customerDAO();
			//Tracks errors with the form fields
			$hasError = false;
			//Array for our error messages
			$errorMessages = Array();

			//Ensure all these values are set.
			//They will only be set when the form is submitted.
			//We only want the code that adds an employee to 
			//the database to run if the form has been submitted.
			// upload file
			if(isset($_FILES['file'])){
				$file_name = $_FILES['file']['name'];
				$file_tmp = $_FILES['file']['tmp_name'];

				if (move_uploaded_file($file_tmp,"files/".$file_name)){
				   echo "File upload Success";
				}else{
					echo "File upload failed";
				}
			 } else {
				 echo "File not set";
			 }

			if( isset($_POST['customerName']) ||
				isset($_POST['phoneNumber'])  || 
				isset($_POST['emailAddress']) ||
				isset($_POST['referral']) ){
			
				// set error if customerName is empty
				if($_POST['customerName'] == ""){
					$errorMessages['customerName'] = "Please enter a name.";
					$hasError = true;
				}
				// set error if phoneNumber is empty or it's not valid phoneNumber format
				if($_POST['phoneNumber'] == "" || !valid_phone_number($_POST['phoneNumber'])){
					$errorMessages['phoneNumber'] = "Please enter a valid phone number.";
					$hasError = true;
				}
				// set error if emailAddress is empty or it's not a valid email format.
				if($_POST['emailAddress'] == "" || !valid_email($_POST['emailAddress'])){
					$errorMessages['emailAddress'] = "Please enter a valid email address.";
					$hasError = true;
				}
				// set error if referral is empty
				if($_POST['referral'] == ""){
					$errorMessages['referral'] = "Please enter referral.";
					$hasError = true;
				}
				// check if email is already exist in datebase.
				$customer = $customerDAO->getCustomer($_POST['emailAddress']);
				if ($customer) {
					$errorMessages['emailAddress'] = "The email address has been used, please use a different one.";
					$hasError = true;
				}
				// if no errors, add customer to datebase
				if(!$hasError){
					$id = 0;
					$customer = new Customer($id, $_POST['customerName'], $_POST['phoneNumber'],
											 $_POST['emailAddress'], $_POST['referral']);
					$addSuccess = $customerDAO->addCustomer($customer);
					echo '<h3>' . $addSuccess . '</h3>';

					
				}
			}
		}  
		catch(Exception $e){
            //If there were any database connection/sql issues,
            //an error message will be displayed to the user.
            echo '<h3>Error on page.</h3>';
            echo '<p>' . $e->getMessage() . '</p>';            
        }
	?>
    <form name="frmNewsletter" id="frmNewsletter" method="post" action="contact.php">
	<table>
    <tr>	 
    <td>Name:</td>
    <td>
		<input type="text" name="customerName" id="customerName" size='40'>
	    <?php 
			//If there was an error with the customerName field, display the message
			if(isset($errorMessages['customerName'])){
					echo '<span style=\'color:red\'>' . $errorMessages['customerName'] . '</span>';
			}
		?>
	</td>
    </tr>
    <tr>
    <td>Phone Number:</td>
    <td>
		<input type="text" name="phoneNumber" id="phoneNumber" size='40'>
    	<?php 
			//If there was an error with the customerName field, display the message
			if(isset($errorMessages['phoneNumber'])){
					echo '<span style=\'color:red\'>' . $errorMessages['phoneNumber'] . '</span>';
			}
		?>
	</td>	
	</tr>
    <tr>
    <td>Email Address:</td>
    <td>
		<input type="text" name="emailAddress" id="emailAddress" size='40'>
		<?php 
			//If there was an error with the customerName field, display the message
			if(isset($errorMessages['emailAddress'])){
					echo '<span style=\'color:red\'>' . $errorMessages['emailAddress'] . '</span>';
			}
		?>
		</tr>
    <tr>
    <td>How did you hear<br> about us?</td>
    <td>
		<input type="text" name="referral" id="referral" size='40'>
		<?php 
			//If there was an error with the customerName field, display the message
			if(isset($errorMessages['referral'])){
					echo '<span style=\'color:red\'>' . $errorMessages['referral'] . '</span>';
			}
		?>
    <tr>
		<td colspan='2'>
			<input type='file' name='file' id='file' value='Choose file!'>&nbsp;&nbsp;
			<input type='submit' name='btnSubmit' id='btnSubmit' value='Sign up!'>&nbsp;&nbsp;
			<input type='reset' name="btnReset" id="btnReset" value="Reset Form">
		</td>
    </tr>
    </table>
    </form>

    </div><!-- End Main -->
    </div><!-- End Content -->

<?php include 'footer.php' ?>
