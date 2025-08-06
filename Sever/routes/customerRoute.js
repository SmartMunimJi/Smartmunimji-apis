// const db = require("../utils/dbpool")
// const {apiSuccess, apiError} = require("../utils/apiresult")
// const express = require("express")
// const bcrypt = require("bcrypt")
// const axios = require("axios")
// const {createToken} = require("../utils/jwtauth")
// const {SMART_MUNIMJI_SECRET_KEY , ECOMMERCE_VALIDATE_PURCHASE_URL} = require("../config")


// const router = express.Router()  
// // customer register 
// router.post("/signup", (req, resp)=> {
//     const {name, email, password, phone, address} = req.body
//     const encPassword = bcrypt.hashSync(password, 10)
//     const enabled = 1

//     db.query("INSERT INTO customers (name, email, password, phone, address) VALUES (?, ?, ?, ?, ?)",
//         [name, email, encPassword, phone, address], (err, result) => {
//  if(err)
//                 return resp.send(apiError(err))
//             // if user inserted successfully, return new user object
//             if(result.affectedRows === 1) {
//                 db.query("SELECT * FROM customers WHERE id=?", [result.insertId],
//                     (err, results) => {
//                         if(err)
//                             return resp.send(apiError(err))
//                         resp.send(apiSuccess(results[0]))
//                     }
//                 )
//             }
//     }
//     )
// })
// router.post("/signin", (req, res) => {
//     const { email, password } = req.body;

//     if (!email || !password)
//         return res.send(apiError("Email and password are required."));

//     db.query("SELECT * FROM customers WHERE email = ?", [email], (err, result) => {
//         if (err) return res.send(apiError("Database error: " + err.message));

//         if (result.length !== 1)
//             return res.send(apiError("Invalid email or user not found."));

//         const dbUser = result[0];

//         const isMatching = bcrypt.compareSync(password, dbUser.password);
//         if (!isMatching)
//             return res.send(apiError("Invalid password."));

//         const token = createToken({
//             id: dbUser.id,
//             email: dbUser.email,
//             role: "customer" 
//         });

//         // remove sensitive data before sending
//         delete dbUser.password;

//         res.send(apiSuccess({
//             ...dbUser,
//             token
//         }));
//     });
// });
// router.post("/registerProduct", async (req, res) => {
//   const { order_id, phone, date_of_purchase } = req.body;

//   if (!order_id || !phone || !date_of_purchase) {
//     return res.status(400).json({ success: false, message: "Missing required fields" });
//   }

//   try {
//     // Step 1: Validate product with E-commerce API
//     const response = await axios.post(
      
//       ECOMMERCE_VALIDATE_PURCHASE_URL,
//       { order_id, phone, date_of_purchase },
//       { headers: { Authorization: SMART_MUNIMJI_SECRET_KEY } }

//     );
//  console.log(SMART_MUNIMJI_SECRET_KEY)
//   console.log(ECOMMERCE_VALIDATE_PURCHASE_URL)
//     const data = response.data;

//     if (!data.success) {
//       return res.status(400).json({ success: false, message: "Invalid purchase" });
//     }

//    const product_id = data?.data?.product?.product_id;

// if (!product_id) {
//   return res.status(400).json({ success: false, message: "Product ID missing in validation response" });
// }


//     // Step 2: Get customer_id from Smart Munimji DB using phone
//     db.query("SELECT id FROM customers WHERE phone = ?", [phone], (err, results) => {
//       if (err) {
//         console.error("Database error:", err);
//         return res.status(500).json({ success: false, message: "Database error" });
//       }

//       if (results.length === 0) {
//         return res.status(404).json({ success: false, message: "Customer not found" });
//       }

//       const customer_id = results[0].id;

//       // Step 3: Insert into registered_products table
//       const insertQuery = `
//         INSERT INTO registered_products (order_id, product_id, customer_id, date_of_purchase)
//         VALUES (?, ?, ?, ?)
//       `;
//       const values = [order_id, product_id, customer_id, date_of_purchase];

//       db.query(insertQuery, values, (insertErr, insertResult) => {
//         if (insertErr) {
//           console.error("Insert error:", insertErr);
//           return res.status(500).json({ success: false, message: "Failed to register product" });
//         }

//         return res.status(200).json({ success: true, message: "Product registered successfully" });
//       });
//     });

//   } catch (error) {
//     console.error("Validation API Error:", error.message || error);
//     return res.status(500).json({ success: false, message: "Failed to validate purchase" });
//   }
// });

// module.exports = router;