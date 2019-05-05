import React, { Component, lazy, Suspense } from 'react';
import { Bar, Line } from 'react-chartjs-2';
import {
  Badge,
  Button,
  ButtonDropdown,
  ButtonGroup,
  ButtonToolbar,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  CardTitle,
  Col,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Form,
  Progress,
  Row,
  Input,
  InputGroup,
  InputGroupAddon,
  Table,
  ListGroup,
  ListGroupItem
} from 'reactstrap';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities'


class Subscribe extends Component {
  constructor(props) {
    super(props);

    this.state = {
      subMail: '',
      unsubMail: '',
    };
  }

  subscribe = () => {
      console.log("Subscribing "+this.state.subMail);
  }

  unsubscribe = () => {
      console.log("Unsubscribing "+this.state.unsubMail);
  }

  handleChange = (e) => {
      this.setState({ [e.target.name] : e.target.value });
  }

  render() {

    return (
      <div className="animated fadeIn">
        <Row>
          <Col sm="12" xl="6">
            <Card>
                <CardHeader>
                    <i className="fa fa-envelope"></i><strong>Subscribe to Mail List</strong>
                </CardHeader>
                <CardBody>
                    <Form>
                        <InputGroup>
                            <InputGroupAddon addonType="prepend">Email</InputGroupAddon>
                            <Input placeholder="abc@xyz.com" name="subMail" value={this.state.subMail} onChange={this.handleChange}/>
                            <InputGroupAddon addonType="append">
                                <Button onClick={this.subscribe}>Subscribe</Button>
                            </InputGroupAddon>
                        </InputGroup>
                    </Form>
                </CardBody>
            </Card>
          </Col>
          <Col sm="12" xl="6">
            <Card>
                <CardHeader>
                    <i className="fa fa-envelope"></i><strong>Unsubscribe from Mail List</strong>
                </CardHeader>
                <CardBody>
                    <Form>
                        <InputGroup>
                            <InputGroupAddon addonType="prepend">Email</InputGroupAddon>
                            <Input placeholder="abc@xyz.com" name="unsubMail" value={this.state.unsubMail} onChange={this.handleChange}/>
                            <InputGroupAddon addonType="append">
                                <Button onClick={this.unsubscribe}>Unsubscribe</Button>
                            </InputGroupAddon>
                        </InputGroup>
                    </Form>
                </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    );
  }
}

export default Subscribe;
