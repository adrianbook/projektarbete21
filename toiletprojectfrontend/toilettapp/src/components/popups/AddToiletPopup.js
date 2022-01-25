import { sendNewToiletToServer } from "../../servercalls/Calls"

const AddToiletPopup = (props) => {
    const addToilet = () => {
        sendNewToiletToServer({latitude: props.marker.thispos[0], longitude:  props.marker.thispos[1]})
        .then(marker => {
            props.updateMarker(marker)
        })
        .catch(e => {
            prompt(e.message)
            console.log(e)
        })
    }
    return(
        <>
        <div style={{display: props.displayMe}}>
            <button onClick={addToilet}>
                Add Toilet here
            </button>
        </div>
        </>
    )
}

export default AddToiletPopup