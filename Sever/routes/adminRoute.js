const db = require("../utils/dbpool");
const { apiSuccess, apiError } = require("../utils/apiresult");
const express = require("express");
const bcrypt = require("bcrypt");
const { createToken } = require("../utils/jwtauth");

const router = express.Router();

// Admin signup
router.post("/signup", (req, res) => {
    const { name, email, password, phone } = req.body;
    const encPassword = bcrypt.hashSync(password, 10);

    db.query(
        "INSERT INTO admins (name, email, password, phone) VALUES (?, ?, ?, ?)",
        [name, email, encPassword, phone],
        (err, result) => {
            if (err) return res.send(apiError(err));

            if (result.affectedRows === 1) {
                db.query("SELECT * FROM admins WHERE id = ?", [result.insertId], (err, results) => {
                    if (err) return res.send(apiError(err));
                    res.send(apiSuccess(results[0]));
                });
            }
        }
    );
});

// Admin signin
router.post("/signin", (req, res) => {
    const { email, password } = req.body;

    if (!email || !password)
        return res.send(apiError("Email and password are required."));

    db.query("SELECT * FROM admins WHERE email = ?", [email], (err, result) => {
        if (err) return res.send(apiError("Database error: " + err.message));
        if (result.length !== 1)
            return res.send(apiError("Invalid email or user not found."));

        const dbUser = result[0];
        const isMatching = bcrypt.compareSync(password, dbUser.password);
        if (!isMatching)
            return res.send(apiError("Invalid password."));

        const token = createToken({
            id: dbUser.id,
            email: dbUser.email,
            role: "admin"
        });

        delete dbUser.password;
        res.send(apiSuccess({
            ...dbUser,
            token
        }));
    });
});

module.exports = router;
