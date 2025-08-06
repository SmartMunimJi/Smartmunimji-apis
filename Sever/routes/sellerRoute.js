const express = require("express");
const router = express.Router();
const multer = require("multer");
const path = require("path");
const db = require("../utils/dbpool");

// Database connection
const db = mysql.createConnection({
  host: "localhost",
  user: "your_user",
  password: "your_password",
  database: "your_db",
});

// Storage config for Multer
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, "uploads/sellerImages/");
  },
  filename: function (req, file, cb) {
    const uniqueSuffix = Date.now() + "-" + Math.round(Math.random() * 1E9);
    const ext = path.extname(file.originalname);
    cb(null, "seller-" + uniqueSuffix + ext);
  }
});

const upload = multer({ storage: storage });

// REGISTER seller with image
router.post("/register", upload.single("shop_image"), (req, res) => {
  const {
    sellername,
    sellercontact,
    shopname,
    sellersemail,
    shopaddress,
    city,
    pincode,
    category,
    password
  } = req.body;

  const shop_image = req.file ? req.file.filename : null;

  const query = `
    INSERT INTO sellers 
    (sellername, sellercontact, shopname, sellersemail, shopaddress, city, pincode, category, password, shop_image) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
  `;

  db.query(query, [sellername, sellercontact, shopname, sellersemail, shopaddress, city, pincode, category, password, shop_image], (err, result) => {
    if (err) {
      console.error("Error registering seller:", err);
      return res.status(500).json({ message: "Server error" });
    }

    res.status(200).json({ message: "Seller registered successfully" });
  });
});


// Seller signin
router.post("/signin", (req, res) => {
    const { email, password } = req.body;

    if (!email || !password)
        return res.send(apiError("Email and password are required."));

    db.query("SELECT * FROM sellers WHERE email = ?", [email], (err, result) => {
        if (err) return res.send(apiError("Database error: " + err.message));
        if (result.length !== 1)
            return res.send(apiError("Invalid email or user not found."));

        const dbSeller = result[0];
        const isMatching = bcrypt.compareSync(password, dbSeller.password);
        if (!isMatching)
            return res.send(apiError("Invalid password."));

        const token = createToken({
            id: dbSeller.id,
            email: dbSeller.email,
            role: "seller"
        });

        delete dbSeller.password;
        res.send(apiSuccess({
            ...dbSeller,
            token
        }));
    });
});

module.exports = router;
