import ProjectListToolbar from "./ProjectListToolbar";
import ListView from "../list/ListView";
import {Box, Checkbox, TableCell, TableRow} from "@mui/material";
import {Component} from "react";


class ProjectDashboard extends Component {

    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            selectedProjects: [],
        };
    }

    handleSelectAll(event) {

    }

    render() {
        return (
            <>
                <ProjectListToolbar />
                <Box sx={{ mt: 3 }}>
                    <ListView
                        header={() => <TableRow>
                            <TableCell padding="checkbox">
                                <Checkbox
                                    checked={ this.state.selectedProjects.length === this.state.projects.length }
                                    color="primary"
                                    indeterminate={
                                        this.state.selectedProjects.length > 0
                                        && this.state.selectedProjects.length < this.state.projects.length
                                    }
                                    onChange={ this.handleSelectAll }
                                />
                            </TableCell>
                            <TableCell>
                                Name
                            </TableCell>
                            <TableCell>
                                Email
                            </TableCell>
                            <TableCell>
                                Location
                            </TableCell>
                            <TableCell>
                                Phone
                            </TableCell>
                            <TableCell>
                                Registration date
                            </TableCell>
                        </TableRow>}
                        elements={ this.state.projects }
                        page="1"
                        maxPage="10"
                        startingValue="6"
                    />
                </Box>
            </>
        );
    }
}

export default ProjectDashboard;
