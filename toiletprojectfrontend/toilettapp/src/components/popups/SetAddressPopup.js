import { useEffect, useState } from "react";

const SetAddressPopup = (props) => {
    const [location, setLocation] = useState({});
   
    useEffect(() =>{
        const getAddress = async (props) => {
            const GEOCODE_URL =
        'https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/reverseGeocode?f=json&location=';
            const coordinatesString = props.marker.thispos[1]+','+props.marker.thispos[0];    
            const response = await fetch(GEOCODE_URL+coordinatesString);
            const responseJson = await response.json();
            setLocation(responseJson);
        };
        getAddress(props)   
    }, []);

    return (
        <>
            <div>
               {location?.address?.Match_addr}
            </div>
        </>
    )   
}

export default SetAddressPopup