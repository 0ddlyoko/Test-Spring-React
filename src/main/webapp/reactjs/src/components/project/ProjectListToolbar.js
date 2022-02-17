import {Box, Button, Card, CardContent, InputAdornment, SvgIcon, TextField, Typography} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import {Component} from "react";


class ProjectListToolbar extends Component {

    inputChange(event) {
        // TODO
        const value = event.target.value;
        console.log(value);
    }

    render() {
        return (
            <Box>
                <Box
                    sx={{
                        alignItems: "center",
                        display: "flex",
                        justifyContent: "space-between",
                        flexWrap: "wrap",
                        m: -1,
                    }}
                >
                    <Typography
                        sx={{ m: 1 }}
                        variant="h4">
                        Projects
                    </Typography>
                    <Box sx={{ m: 1 }}>
                        <Button
                            color="primary"
                            variant="contained">
                            Add project
                        </Button>
                    </Box>
                </Box>
                <Box sx={{ mt: 3 }}>
                    <Card>
                        <CardContent>
                            <Box>
                                <TextField
                                    fullWidth
                                    InputProps={{
                                        startAdornment: (
                                            <InputAdornment position="start">
                                                <SvgIcon
                                                    color="action"
                                                    fontSize="small"
                                                >
                                                    <SearchIcon />
                                                </SvgIcon>
                                            </InputAdornment>
                                        )
                                    }}
                                    placeholder="Search product"
                                    variant="outlined"
                                    onChange={ this.inputChange }
                                />
                            </Box>
                        </CardContent>
                    </Card>
                </Box>
            </Box>
        );
    }
}

export default ProjectListToolbar;
