import React from 'react';
import Nav from 'react-bootstrap/Nav';

const Footer = () => {
  return (
    <footer className="bg-light fixed-bottom">
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <Nav className="small">
              <Nav.Item>
                <Nav.Link href="/latest">Latest</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link href="/historical">Historical</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link href="/exchange">Exchange</Nav.Link>
              </Nav.Item>
            </Nav>
          </div>
            <div className="col-md-6">
              <Nav className="small justify-content-end">
                <Nav.Item>
                  <Nav.Link href="/about">About</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link href="/contact">Contact</Nav.Link>
                </Nav.Item>
              </Nav>
            </div>
        </div>
        <div className="row mt-2">
          <div className="col-md-12 text-right">
            <Nav className="small">
              <Nav.Item>
                <Nav.Link href="https://github.com/surfacewaves">GitHub</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link href="https://t.me/klch_o">Telegram</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link href="https://www.mirea.ru/">MIREA</Nav.Link>
              </Nav.Item>
            </Nav>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
