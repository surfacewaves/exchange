import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Container from 'react-bootstrap/Container';
import 'bootstrap/dist/css/bootstrap.min.css';

const Header = () => {
  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/home">Лого</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav" className="justify-content-center">
          <Nav className="mr-auto">
            <Nav.Link as={Link} to="/latest">Latest</Nav.Link>
            <Nav.Link as={Link} to="/historical">Historical</Nav.Link>
            <Nav.Link as={Link} to="/exchange">Exchange</Nav.Link>
          </Nav>
        </Navbar.Collapse>
        <Nav>
          <Nav.Link as={Link} to="/account">Account</Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default Header;
