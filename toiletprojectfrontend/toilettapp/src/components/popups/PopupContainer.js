import { useEffect, useState } from "react"
import AddRatingPopup from "./AddRatingPopup"
import AddReportPopup from "./AddReportPopup"
import AddToiletPopup from "./AddToiletPopup"
import DefaultPopup from "./DefaultPopup"
import SetAddressPopup from "./SetAddressPopup"

const PopupContainer = (props) => {
    const [marker, setMarker] = useState(props.marker)
    const [location, setLocation] = useState({});
   
    const noView = {
        default: "none",
        rating: "none",
        report: "none",
        toilet: "none"
        }
    const [view, setView] = useState({
        ...noView,
        default: ""
    })

    useEffect(()=> {
        setMarker(props.marker)
    },[props.marker, setMarker])

    useEffect(()=>{
        const getAddress = async (marker) => {
            const GEOCODE_URL =
        'https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/reverseGeocode?f=json&location=';
            const coordinatesString = marker.thispos[1]+','+marker.thispos[0];    
            const response = await fetch(GEOCODE_URL+coordinatesString);
            const responseJson = await response.json();
            setLocation(responseJson);
        };
        getAddress(marker)
    },[marker, setLocation])

    useEffect(() => {
        if (Object.getOwnPropertyNames(view).includes(props.type)) {
            setView({
                ...noView,
                [props.type]: ""
            })
        }
    },[])
    
    const changeView = (newView) => {
        const viewToSet = newView || "default"
        setView({
            ...noView,
            [viewToSet]: ""
        })
    }
    const changeMarker = (marker) => {
        console.log("_____" + marker.id)
        setMarker(marker)
        setView({
            ...noView,
            default: ""
        })
    }

    const updateMarker = marker => {
        props.addMarker(marker)
        setMarker(marker)
    }


    return (
        <>
            <SetAddressPopup 
                marker={marker}
                loc={location}

            />
            <AddRatingPopup
                displayMe={view.rating}
                changeView={changeMarker}
                marker={marker}
                updateMarker={updateMarker}
            />
            <DefaultPopup
                displayMe={view.default}
                changeView={changeView}
                marker={marker}
            />
            <AddReportPopup
                displayMe={view.report}
                changeView={changeView}
                marker={marker}
            />
            <AddToiletPopup
                displayMe={view.toilet}
                changeView={changeView}
                changeMarker={changeMarker}
                marker={marker}
                updateMarker={updateMarker}
            />
        </>
    )
}

export default PopupContainer