import {getAllRatingsForToilet} from "../../servercalls/Calls";
import {useEffect, useState} from "react";
import Modal from 'react-bootstrap/Modal'
import Card from "react-bootstrap/Card";
import ListGroup from "react-bootstrap/ListGroup";

const SeeRatingsButton = (toiletId) => {
    const [ratings, setRatings] = useState([])
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const getAndShowRatings = () => {
        getAllRatingsForToilet(toiletId.toiletId)
            .then(res => {
                setRatings(res)
            }).catch(e => {
                handleClose()
                window.alert(e.message)
        })
        handleShow()
    }


    return(
        <>
            <button onClick={getAndShowRatings}>
                See Ratings
            </button>

            <Modal style={{height: "30em"}} show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Ratings for toilet with id: {toiletId.toiletId}</Modal.Title>
                </Modal.Header>
                <Modal.Body class="overflow-auto">{ratings.map(rating => {
                    return (
                    <div>
                        <Card style={{margin: "1rem"}}>
                            <Card.Header as="h5">Rating</Card.Header>
                            <ListGroup variant="flush">
                                <ListGroup.Item>Rating User: {rating.toiletUser.name}</ListGroup.Item>
                                <ListGroup.Item>Rating: {rating.rating}</ListGroup.Item>
                                <ListGroup.Item><p>Notes:</p><p>{rating.notes}</p></ListGroup.Item>
                            </ListGroup>
                        </Card>
                    </div>)

                })}</Modal.Body>
            </Modal>
        </>

    )
}

export default SeeRatingsButton