<?php

class WebsiteUser{
    /* Host address for the database */
    protected static $DB_HOST = "127.0.0.1";
    /* Database username */
    protected static $DB_USERNAME = "wp_eatery";
    /* Database password */
    protected static $DB_PASSWORD = "password";
    /* Name of database */
    protected static $DB_DATABASE = "wp_eatery";
    
    private $username;
    private $password;
    private $lastlogin;
    private $adminID;
    private $mysqli;
    private $dbError;
    private $authenticated = false;
    
    function __construct() {
        $this->mysqli = new mysqli(self::$DB_HOST, self::$DB_USERNAME, 
                self::$DB_PASSWORD, self::$DB_DATABASE);
        if($this->mysqli->errno){
            $this->dbError = true;
        }else{
            $this->dbError = false;
        }
    }
    public function authenticate($username, $password){
        $loginQuery = "SELECT * FROM adminusers WHERE Username = ? AND Password = ?";
        $stmt = $this->mysqli->prepare($loginQuery);
        $stmt->bind_param('ss', $username, $password);
        $stmt->execute();
        $result = $stmt->get_result();
        if($result->num_rows == 1){
            $this->username = $username;
            $this->password = $password;
            $this->authenticated = true;
            $row = $result->fetch_assoc();
            $this->lastlogin = $row["Lastlogin"];
            $this->adminID = $row["AdminID"];
            $stmt->free_result();

            // now update lastlogin 
            $curdate = date("Y-m-d");
            $updatequery = "UPDATE adminusers SET Lastlogin = ? WHERE AdminID = ?";
            $stmt = $this->mysqli->prepare($updatequery);
            $stmt->bind_param('ss', $curdate, $this->adminID);
            $stmt->execute();
        }
        $stmt->free_result();
    }

    public function isAuthenticated(){
        return $this->authenticated;
    }
    public function hasDbError(){
        return $this->dbError;
    }
    public function getUsername(){
        return $this->username;
    }
    public function getLastlogin(){
        return $this->lastlogin;
    }
    public function getAdminID() {
        return $this->adminID;
    }
}
?>
