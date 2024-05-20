import React from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

const News = () => {
  return (
    <Row>
      <Col md={6}>
        <Card className="mb-4">
          <Card.Body>
            <Card.Title>Latest News 1</Card.Title>
            <Card.Text>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eget lectus ac nulla sollicitudin lacinia. Quisque tempor justo vitae urna congue rutrum.
            </Card.Text>
            <Button variant="primary" href="/home">Read More</Button>
          </Card.Body>
        </Card>
      </Col>
      <Col md={6}>
        <Card className="mb-4">
          <Card.Body>
            <Card.Title>Latest News 2</Card.Title>
            <Card.Text>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla eget lectus ac nulla sollicitudin lacinia. Quisque tempor justo vitae urna congue rutrum.
            </Card.Text>
            <Button variant="primary" href="/home">Read More</Button>
          </Card.Body>
        </Card>
      </Col>
    </Row>
  );
};

export default News;
