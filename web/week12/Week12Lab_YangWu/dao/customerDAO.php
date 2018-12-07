<?php
require_once('abstractDAO.php');
require_once('./model/customer.php');
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of employeeDAO
 *
 * @author Matt
 */
class customerDAO extends abstractDAO {
        
    function __construct() {
        try{
            parent::__construct();
        } catch(mysqli_sql_exception $e){
            throw $e;
        }
    }
    
    /*
     * This is an example of how to use the query() method of a mysqli object.
     * 
     * Returns an array of <code>Employee</code> objects. If no employees exist, returns false.
     */
    public function getCustomers(){
        //The query method returns a mysqli_result object
        $result = $this->mysqli->query('SELECT * FROM mailinglist');
        if (!$result) {
            return false;
        }
        $customers = Array();
        if($result->num_rows >= 1){
            while($row = $result->fetch_assoc()){
                //Create a new employee object, and add it to the array.
                $customer = new Customer($row['_id'], $row['customerName'], $row['phoneNumber'], $row['emailAddress'], $row['referrer']);
                $customers[] = $customer;
            }
            $result->free();
            return $customers;
        }
        $result->free();
        return false;
    }
    /*
     * This is an example of how to use a prepared statement
     * with a select query.
     */
    public function getCustomer($emailAddress){
        $query = 'SELECT * FROM mailinglist WHERE emailAddress = ?';
        $stmt = $this->mysqli->prepare($query);
        if (!$stmt) {
            return false;
        }
        $stmt->bind_param('s', $emailAddress);
        $stmt->execute();
        $result = $stmt->get_result();
        if($result->num_rows == 1){
            $temp = $result->fetch_assoc();
            $customer = new Customer($temp['_id'], $temp['customerName'], $temp['phoneNumber'], $temp['emailAddress'], $temp['referrer']);
            $result->free();
            return $customer;
        }
        $result->free();
        return false;
    }

    public function addCustomer($customer){
         if(!$this->mysqli->connect_errno){
            //The query uses the question mark (?) as a
            //placeholder for the parameters to be used
            //in the query.
            $query = 'INSERT INTO mailinglist VALUES (?,?,?,?,?)';
            //The prepare method of the mysqli object returns
            //a mysqli_stmt object. It takes a parameterized 
            //query as a parameter.
            if (!$stmt = $this->mysqli->prepare($query)) {
				$error = $this->mysqli->errno . ' ' . $this->mysqli->error;
				echo $error;
				return $stmt->error;
			}
            //The first parameter of bind_param takes a string
            //describing the data. In this case, we are passing 
            //three variables: an integer(employeeId), and two
            //strings (firstName and lastName).
            //
            //The string contains a one-letter datatype description
            //for each parameter. 'i' is used for integers, and 's'
            //is used for strings.
			
 			$name  = $customer->getCustomerName();
            $phone = $customer->getPhoneNumber();
            $email = $customer->getEmailAddress();
			$refer = $customer->getReferrer();
			$stmt->bind_param('issss', $id,
					$name, $phone, $email, $refer);

            //Execute the statement
            $stmt->execute();
            //If there are errors, they will be in the error property of the
            //mysqli_stmt object.
            if($stmt->error){
                return $stmt->error;
            } else {
                return $customer->getCustomerName() . ' ' . $customer->getPhoneNumber() . ' added successfully!';
            }
        } else {
            return 'Could not connect to Database.';
        }
    }
    
    public function deleteCustomer($cutomerEmail){
        if(!$this->mysqli->connect_errno){
            $query = 'DELETE FROM mailingList WHERE emailAddress = ?';
            $stmt = $this->mysqli->prepare($query);
            $stmt->bind_param('i', $cutomerEmail);
            $stmt->execute();
            if($stmt->error){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    public function editEmployee($customerName, $phoneNumber, $emailAddress, $referrer){
        if(!$this->mysqli->connect_errno){
            $query = 'UPDATE employees SET customerName = ?, phoneNumber = ?, referrer = ?, WHERE emailAddress = ?';
            $stmt = $this->mysqli->prepare($query);
            $stmt->bind_param('ssss', $customerName, $phoneNumber, $referrer, $emailAddress);
            $stmt->execute();
            if($stmt->error){
                return false;
            } else {
                return $stmt->affected_rows;
            }
        } else {
            return false;
        }
    }
}

?>
