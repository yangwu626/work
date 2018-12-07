<?php include 'header.php' ?>
<?php
    if(!isset($_SESSION['websiteUser'])){
        $_SESSION['current_page'] = $_SERVER['REQUEST_URI'];
        header("Location:login.php");
        exit();
    }
?>

<?php require_once('./dao/customerDAO.php'); ?>
<?php require_once('PasswordHash.php'); ?>

<!DOCTYPE html>
<html>
	<body>
        <h1>List of all customers</h1>
        <?php
            //This section will display an HTML table containing all
            //the employees in the employees table. The getEmployees()
            //method of the employeeDAO class returns an array of employees
            //(if there are any).
            //
            echo '<p>Session AdminID: ' . $_SESSION["AdminID"] . '</p>';
            echo '<p>Lastlogin date: ' . $_SESSION['Lastlogin'] . '</p>';

            try{
                $hash = new PasswordHash(8, false);

                $customerDAO = new customerDAO();
                $customers = $customerDAO->getCustomers();
                if($customers){
                    //We only want to output the table if we have employees.
                    //If there are none, this code will not run.
                    echo '<table border=\'1\'>';
                    echo '<tr><th>ID</th><th>customerName</th><th>phoneNumber</th><th>emailAddress</th><th>referral</th></tr>';
                    foreach($customers as $customer){
                        $hashedEmail = $hash->HashPassword($customer->getEmailAddress());
                        echo '<tr>';
                        echo '<td>' . $customer->getId() . '</td>';
                        echo '<td>' . $customer->getCustomerName() . '</td>';
                        echo '<td>' . $customer->getPhoneNumber() . '</td>';
                        echo '<td>' . $hashedEmail . '</td>';
                        echo '<td>' . $customer->getReferrer() . '</td>';
                        echo '</tr>';
                    }
                }
            }catch(Exception $e){
                //If there were any database connection/sql issues,
                //an error message will be displayed to the user.
                echo '<h3>Error on page.</h3>';
                echo '<p>' . $e->getMessage() . '</p>';            
            }
        ?>
    </body>
</html>		
