import ProjectListToolbar from "./ProjectListToolbar";
import {Box, Checkbox, Table, TableBody, TableCell, TableHead, TableRow} from "@mui/material";
import {Component} from "react";
import axios from "axios";


class ProjectDashboard extends Component {

    constructor(props) {
        super(props);
        this.state = {
            list: [],
            selected: new Set(),
        };
    }

    componentDidMount() {
        axios.get("http://localhost:8080/api/project")
            .then(value => {
                const projects = value.data;
                this.setState({
                    list: projects["content"],
                    page: projects["page"],
                    size: projects["size"],
                    totalElements: projects["totalElements"],
                    totalPages: projects["totalPages"],
                });
            })
    }

    handleSelectAll() {
        if (this.state.selected.size === this.state.list.length) {
            this.setState({
                selected: new Set(),
            })
        } else {
            this.setState({
                selected: new Set(this.state.list),
            })
        }
    }

    handleSelectProject(project) {
        const lst = this.state.selected;
        if (lst.has(project)) {
            lst.delete(project);
            this.setState({
                selected: lst,
            })
        } else {
            lst.add(project);
            this.setState({
                selected: lst,
            })
        }
    }

    render() {
        return (
            <>
                <ProjectListToolbar />
                <Box sx={{ mt: 3 }}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell padding="checkbox">
                                    <Checkbox
                                        checked={ this.state.selected.size === this.state.list.length }
                                        color="primary"
                                        indeterminate={
                                            this.state.selected.size     > 0
                                            && this.state.selected.size < this.state.list.length
                                        }
                                        onChange={ () => this.handleSelectAll() }
                                    />
                                </TableCell>
                                <TableCell>
                                    ID
                                </TableCell>
                                <TableCell>
                                    Name
                                </TableCell>
                                <TableCell>
                                    Number of tasks
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.list.map(project => (
                                <TableRow key={ project.id }>
                                    <TableCell padding="checkbox">
                                        <Checkbox
                                            checked={ this.state.selected.has(project) }
                                            color="primary"
                                            onChange={ () => this.handleSelectProject(project) }
                                        />
                                    </TableCell>
                                    <TableCell>
                                        { project["id"] }
                                    </TableCell>
                                    <TableCell>
                                        { project["name"] }
                                    </TableCell>
                                    <TableCell>
                                        { project["tasks"].length }
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Box>
            </>
        );
    }
}

export default ProjectDashboard;
