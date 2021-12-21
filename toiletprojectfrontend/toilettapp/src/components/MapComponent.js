import { MapContainer, TileLayer, Marker, Popup , useMap} from 'react-leaflet';
import React, { useEffect, useState } from "react";


function LocationMarker() {
  const [position, setPosition] = useState(null);
  const map = useMap();


  useEffect(() => {
    map.locate().on("locationfound", function (e) {
      setPosition(e.latlng);
      map.flyTo(e.latlng, map.getZoom());
    });
  }, [map]);

  return position === null ? null : (
      <Marker position={position}>
        <Popup>
          You are here. <br />
        </Popup>
      </Marker>
  );
}
const MapComponent = (props) => {
return (
  <MapContainer center={props.pos} zoom={props.zoom} scrollWheelZoom={props.scrollwheel} id="map">
  <TileLayer
    attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
  />
  <LocationMarker />
  {props.markers.map( marker => (

    <Marker position={marker} key={props.markers.indexOf(marker)}>
    <Popup>
      A pretty CSS3 popup. <br /> Easily customizable.
    </Popup>
  </Marker>
      ))
  }
</MapContainer>
)}

export default MapComponent