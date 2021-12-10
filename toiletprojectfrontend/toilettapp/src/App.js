
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MapPage from "./routes/MapPage";
import Login from "./routes/Login";
import CreateUser from "./routes/CreateUser";
import AddToilet from "./routes/AddToilet";

const App = () => {

    return (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<MapPage/>} />
      <Route path="/login" element={<Login/>}/>
      <Route path="/createuser" element={<CreateUser/>}/>
        <Route path="/addToilet" element={<AddToilet/>}/>
    </Routes>
  </BrowserRouter>
        )}

export default App;