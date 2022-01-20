import { sendNewToiletToServer } from "../../servercalls/Calls"

const AddToiletPopup = (props) => {
    const addToilet = () => {
        sendNewToiletToServer(props.marker)
        .then(toilet => {
            props.addMarker({thispos:[toilet.latitude, toilet.longitude], id: toilet.id})
            props.changeView()
        })
        .catch(e => {
            prompt(e.message)
            console.log(e)
        })
    }
    return(
        <>
        <div style={{display: props.displayMe}}>
            hej
            <button onClick={addToilet}>
                Add Toilet here
            </button>
        </div>
        </>
    )
}

export default AddToiletPopup