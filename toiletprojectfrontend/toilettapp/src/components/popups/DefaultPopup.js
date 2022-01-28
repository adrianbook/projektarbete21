import SeeRatingsButton from "./SeeRatingsButton";

const DefaultPopup = (props) => {
    const handleChange = e => {
        props.changeView(e.target.name)
    }
     let descriptions = {
         urinal: "Urinal",
         separateGenders: "Separate Genders",
         changingTable: "Changing Table",
         shower: "Shower",
         handicapFriendly: "Handicap Friendly"
     }

     let costOrFree = props.marker.cost ? "Pay toilet" : "Free Toilet"

    let avgRating = props.marker.avgRat === 0? "Not rated yet" : props.marker.avgRat


    return (
        <>
        <div style={{display: props.displayMe}}>
        <br />
        <b>Average rating: {avgRating}</b> 
        <br/>
        {costOrFree}
        <div style={{marginTop: "0.5em"}}>
            <ul style={{marginLeft: "0.6em"}}>
                
            {Object.getOwnPropertyNames(props.marker).map(o => {
                if(descriptions.hasOwnProperty(o) && props.marker[o]) {
                    return <li>{descriptions[o]}</li>
                }
                else return
            }
            )}
            </ul>
        </div>
        <button name="rating" onClick={handleChange}>
            Rate toilet
        </button>
        <button name="report" onClick={handleChange}>
            Report toilet
        </button>
            <SeeRatingsButton toiletId={props.marker.id} />
        </div>
        </>
    )
}

export default DefaultPopup