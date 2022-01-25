const DefaultPopup = (props) => {
    const handleChange = e => {
        props.changeView(e.target.name)
    }
    let avgRating = props.marker.avgRat === 0? "Not rated yet" : props.marker.avgRat
    return (
        <>
        <div style={{display: props.displayMe}}>
        ID: {props.marker.id}
        <br />
        Average rating: {avgRating}
        <br/>
        <button name="rating" onClick={handleChange}>
            Rate toilet
        </button>
        <button name="report" onClick={handleChange}>
            Report toilet
        </button>
        </div>
        </>
    )
}

export default DefaultPopup