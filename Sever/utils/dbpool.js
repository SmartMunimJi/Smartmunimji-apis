const mysql = require("mysql2")

const pool = mysql.createPool({
    host:"localhost",
    port: 3306,
    user: "smartmunimji",
    password: "smartmunimji",
    database:"smartmunimji_db"


})
module.exports = pool