import React, { Component } from 'react';
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Col,
  Row,
  ListGroup,
  ListGroupItem,
  Form,
  FormGroup,
  FormText,
  Input,
  Label,
} from 'reactstrap';


class Articles extends Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.onRadioBtnClick = this.onRadioBtnClick.bind(this);

    this.state = {
      dropdownOpen: false,
      radioSelected: 2,
    };
  }

  toggle() {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen,
    });
  }

  onRadioBtnClick(radioSelected) {
    this.setState({
      radioSelected: radioSelected,
    });
  }

  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col sm="12" xl="12">
            <Card>
              <CardHeader>
                <i className="fa fa-align-justify"></i><strong>Articles</strong>
              </CardHeader>
              <CardBody>
                <ListGroup>
                  <ListGroupItem>Article1</ListGroupItem>
                  <ListGroupItem>Article2</ListGroupItem>
                  <ListGroupItem>Article3</ListGroupItem>
                  <ListGroupItem>Article4</ListGroupItem>
                </ListGroup>
              </CardBody>
            </Card>
          </Col>

        </Row>
      </div>
    );
  }
}

export default Articles;
