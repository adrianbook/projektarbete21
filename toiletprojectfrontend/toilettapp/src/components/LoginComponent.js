import Modal from 'react-bootstrap/Modal'
import {useState} from "react";

const LoginComponent = (props) => {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
        <button onClick={handleShow}>
            Login
        </button>

        <Modal style={{height: "30rem", margin: "5rem"}} show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Login</Modal.Title>
            </Modal.Header>
            <Modal.Body className="overflow-auto">
                <form style={{padding: "2rem"}} onSubmit={props.handleSubmit}>
                    <label>
                        <input
                            type="text"
                            value={props.loginInfo.username}
                            name="username"
                            placeholder="User name here"
                            onChange={props.handleChange}/>
                    </label>
                    <label>
                        <input
                            type="password"
                            value={props.loginInfo.password}
                            name="password"
                            placeholder="Password here"
                            onChange={props.handleChange}/>
                    </label>
                    <button type="submit" onClick={handleClose}>
                        submit
                    </button>
                </form>
            </Modal.Body>
        </Modal>
        </>
    );
}

export default LoginComponent