import logo from './logo.svg';
import './App.css';
import Pang from './components/pang'
import Map from './components/map'

const name = "sprut"

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
                  <Pang kuk = {name}/>
                  <Pang kuk = "snäll"/>
                  <Map />
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
