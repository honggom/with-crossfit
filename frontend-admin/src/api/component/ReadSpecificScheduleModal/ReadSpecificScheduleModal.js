import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const deleteSpecificScheduleById = async (id) => {

    const config = {
        method: 'DELETE',
        url: `${backEndBaseUri}/admin/api/specific-schedule/${id}`,
        withCredentials: true,
    }

    return await axios(config);
};

export { deleteSpecificScheduleById };