import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import {Col, Container, Row} from "react-bootstrap";
import Welcome from "./components/Welcome";
import {Component} from "react";

class App extends Component {

  render() {
    return (
        <BrowserRouter>
          <Container>
            <Row>
              <Col lg={12} className={"margin-top"}>
                <Routes>
                  <Route path="/" element={<Welcome />}/>
                </Routes>
              </Col>
            </Row>
          </Container>
        </BrowserRouter>
    );
  }
}

/*
return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
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
*/

export default App;
