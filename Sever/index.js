const express = require("express");
const app = express();
//const customerRouter = require("./routes/customerRoute");
const adminRouter = require("./routes/adminRoute")
//const sellerRouter = require("./routes/sellerRoute")

const {jwtAuth} = require("./utils/jwtauth")


app.use(jwtAuth);
app.use(express.json());

//app.use("/customer", customerRouter);
app.use("/admin", adminRouter);
//app.use("/seller", sellerRouter);






const port = 3000;
app.listen(port, "0.0.0.0", ()=>{
    console.log("server ready at port", port)
});