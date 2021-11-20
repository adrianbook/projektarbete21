import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet'

const MapComponent = (props) => (
  <MapContainer center={props.pos} zoom={props.zoom} scrollWheelZoom={props.scrollwheel} id="map">
  <TileLayer
    attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
  />
  {props.markers.map( marker => (

    <Marker position={marker} key={props.markers.indexOf(marker)}>
    <Popup>
      A pretty CSS3 popup. <br /> Easily customizable.
    </Popup>
  </Marker>
      ))
  }
</MapContainer>
)

export default MapComponent