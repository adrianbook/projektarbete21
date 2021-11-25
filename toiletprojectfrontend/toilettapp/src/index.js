import { render } from "react-dom";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MapPage from "./routes/MapPage";
import Login from "./routes/Login";
import CreateUser from "./routes/CreateUser";

const rootElement = document.getElementById("root");
render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<MapPage/>} />
      <Route path="/login" element={<Login/>}/>
      <Route path="/createuser" element={<CreateUser/>}/>
    </Routes>
  </BrowserRouter>
  , rootElement);