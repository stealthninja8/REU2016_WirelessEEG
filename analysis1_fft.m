% analysis1.m
% Steve Heinisch, Zoe Kendall, and Sam Morris
% This script [will] read in an Excel file and filter the contained data,
% then epoch it into 1-s segments. Epochs will then be analyzed by an FFT
% and averaged.

close all;
home;
% ========================================================================
%                               CONSTANTS
% ========================================================================
filename = 'C:\Users\zkendall\zkendall\Documents\SPIRE-EIT\srest1.xlsx';
desired_electrode = 'Cz';

sampleRate = 2048;
duration = 10;
N = sampleRate*duration;

% ========================================================================
%                                SCRIPT
% ========================================================================
% read data and get the electrode labels as a cell array
[num, txt, raw] = xlsread(filename);
labels = raw(9, 1:end);

% find the column number of the desired electrode
elec = 1;
for i = 1:length(labels)
    if strcmp(labels(i), desired_electrode) == 1
        elec = i;
        break;
    end
end

% plot the data
data = num(7:end,1:end);
plot(data(1:end,elec))
xlabel('Sample')
ylabel('EEG (V)')
